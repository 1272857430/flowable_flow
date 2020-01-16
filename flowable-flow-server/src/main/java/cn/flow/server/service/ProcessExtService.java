package cn.flow.server.service;

import cn.flow.api.enums.ProcessStatus;
import cn.flow.api.request.process.GetProcessInstanceRequestBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.server.service.dao.ProcessExtDao;
import cn.flow.server.service.entity.ProcessExtEntity;
import cn.flow.server.utils.DateUtil;
import cn.flow.server.utils.RandomNumberUtil;
import cn.flow.server.utils.StringHelper;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class ProcessExtService {

    private static Logger logger = LoggerFactory.getLogger(ProcessExtService.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessExtDao processExtDao;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private HistoryService historyService;


    @Transactional(rollbackFor = Exception.class)
    public ProcessInstanceResponseBody startProcessInstanceWithForm(String processBusinessKey, String userId, String processInstanceName, String processScopeId, Map<String, Object> variables) {
        try{
            ProcessDefinition processDefinition = workFlowService.getProcessDefintionByKey(processBusinessKey);
            identityService.setAuthenticatedUserId(userId);
            logger.info("流程参数: {}", variables);
            ProcessInstance pi = runtimeService.startProcessInstanceWithForm(processDefinition.getId(), "outcome", variables, processInstanceName);
            ProcessInstanceResponseBody res = new ProcessInstanceResponseBody();

            if(!Objects.isNull(pi) && pi instanceof ExecutionEntityImpl){
                ExecutionEntityImpl entity = (ExecutionEntityImpl)pi;
                BeanUtils.copyProperties(entity,res);
                ProcessExtEntity p = new ProcessExtEntity();
                p.setProcessInstanceId(pi.getProcessInstanceId());
                p.setProcessNumber(DateUtil.formatDate(new Date(), DateUtil.FORMAT_YYYYMMDDHHMMSS)+ RandomNumberUtil.random(0,9));
                p.setProcessScopeId(processScopeId);
                p.setBusinessKey(pi.getBusinessKey());
                p.setDeploymentId(pi.getDeploymentId());
                p.setDescription(pi.getDescription());
                p.setProcessDefinitionId(pi.getProcessDefinitionId());
                p.setProcessDefinitionKey(pi.getProcessDefinitionKey());
                p.setProcessDefinitionName(pi.getProcessDefinitionName());
                p.setProcessDefinitionVersion(pi.getProcessDefinitionVersion());
                p.setProcessInstanceName(pi.getName());
                p.setStartTime(pi.getStartTime());
                p.setStartUserId(pi.getStartUserId());
                // TODO 需要引入用户模块
                p.setStartUserName("测试用户");

                String processDescription = processDefinition.getDescription();
                if(!StringUtils.isEmpty(processDescription)){
                    String renderString = StringHelper.renderString(processDescription, variables);
                    p.setDescription(renderString);
                    res.setDescription(renderString);
                    // TODO 需要引入用户模块
                    res.setStartUserName("测试用户");
                }
                p.setStatus(ProcessStatus.UNFINISHED.name());
                ProcessExtEntity processExtEntity = processExtDao.save(p);
                if(Objects.isNull(processExtEntity)){
                    throw new Exception("新建流程实例失败");
                }
                res.setProcessNumber(processExtEntity.getProcessNumber());
                res.setProcessScopeId(processExtEntity.getProcessScopeId());
                return res;
            }else{
                throw new Exception("新建流程实例失败");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public ProcessInstanceResponseBody getSingleProcessInstanceInfo(String processInstanceId) {

        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance)) {
            return null;
        }

        ProcessInstanceResponseBody processInstanceResponseBody = historicProcessInstanceToBody(historicProcessInstance);
        ProcessExtEntity processExtEntity = processExtDao.findByProcessInstanceId(processInstanceId);
        if(!Objects.isNull(processExtEntity)){
            perfectProcessInstance(processInstanceResponseBody,processExtEntity);
        }
        return processInstanceResponseBody;
    }

    /**
     * 历史流程实例对象转换成自定义流程实例对象
     * @param is 历史流程实例对象
     * @return 自定义流程实例对象
     */
    private ProcessInstanceResponseBody historicProcessInstanceToBody(HistoricProcessInstance is){
        ProcessInstanceResponseBody processInstanceBody = new ProcessInstanceResponseBody();
        processInstanceBody.setProcessDefinitionId(is.getProcessDefinitionId());
        processInstanceBody.setProcessDefinitionKey(is.getProcessDefinitionKey());
        processInstanceBody.setProcessInstanceId(is.getId());
        processInstanceBody.setProcessDefinitionName(is.getProcessDefinitionName());
        processInstanceBody.setProcessDefinitionVersion(is.getProcessDefinitionVersion());
        processInstanceBody.setStartTime(is.getStartTime());
        processInstanceBody.setDescription(is.getDescription());
        processInstanceBody.setStartUserId(is.getStartUserId());
        processInstanceBody.setDurationInMillis(is.getDurationInMillis());
        processInstanceBody.setBusinessKey(is.getBusinessKey());
        if(Objects.isNull(is.getEndTime())){
            processInstanceBody.setStatus(ProcessStatus.UNFINISHED.name());
        }else{
            processInstanceBody.setStatus(ProcessStatus.FINISHED.name());
        }
        return processInstanceBody;
    }

    private void perfectProcessInstance(ProcessInstanceResponseBody processInstanceResponseBody,ProcessExtEntity processExtEntity){
        processInstanceResponseBody.setStartUserName(processExtEntity.getStartUserName());
        processInstanceResponseBody.setDescription(processExtEntity.getDescription());
        processInstanceResponseBody.setProcessNumber(processExtEntity.getProcessNumber());
        processInstanceResponseBody.setProcessScopeId(processExtEntity.getProcessScopeId());
    }

    public List<ProcessInstanceResponseBody> getMyStartedProcessInstance(GetProcessInstanceRequestBody requestBody) {
        int p = StringUtils.isEmpty(requestBody.getPageNo()) ? 0 : requestBody.getPageNo() - 1;
        int r = StringUtils.isEmpty(requestBody.getPageSize()) ? 20 : requestBody.getPageSize();
        Page<ProcessExtEntity> beanPage = processExtDao.findAll((Root<ProcessExtEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> andList = new ArrayList<>();
            Path<String> startTimePath = root.get("startTime");
            Path<String> endTimePath = root.get("endTime");
            Path<String> userIdPath = root.get("startUserId");
            Path<String> userNamePath = root.get("startUserName");
            Path<String> statusPath = root.get("status");
            Path<String> processScopeIdsPath = root.get("processScopeIds");
            // 开始时间
            if (!Objects.isNull(requestBody.getStartTime())) {
                Date date = new Date();
                date.setTime(requestBody.getStartTime());
                andList.add(cb.greaterThanOrEqualTo(startTimePath.as(Date.class), date));
            }
            // 结束时间
            if (!Objects.isNull(requestBody.getEndTime())){
                Date date = new Date();
                date.setTime(requestBody.getEndTime());
                andList.add(cb.lessThanOrEqualTo(endTimePath.as(Date.class), date));
            }
            // 发起人Id
            andList.add(cb.equal(userIdPath, requestBody.getUserId()));
            // 发起人姓名
            if (!StringUtils.isEmpty(requestBody.getStartUserName())) {
                cb.like(userNamePath, "%" + requestBody.getStartUserName() + "%");
            }
            // 流程状态
            if (!StringUtils.isEmpty(requestBody.getStatus())){
                andList.add(cb.equal(statusPath, requestBody.getStatus()));
            }
            // 数据权限
            if (!CollectionUtils.isEmpty(requestBody.getProcessScopeIds())) {
                CriteriaBuilder.In<String> in = cb.in(processScopeIdsPath);
                requestBody.getProcessScopeIds().forEach(in::value);
                andList.add(in);
            }
            // 流程类型
            if (!CollectionUtils.isEmpty(requestBody.getProcessDefinitionKeys())) {
                CriteriaBuilder.In<String> in = cb.in(processScopeIdsPath);
                requestBody.getProcessDefinitionKeys().forEach(in::value);
                andList.add(in);
            }
            Predicate all = cb.and(andList.toArray(new Predicate[andList.size()-1]));
            query.orderBy(cb.desc(startTimePath));
            query.where(all);
            return query.getRestriction();

        }, new PageRequest(p, r));

        return processExtEntityToBody(beanPage.getContent());
    }

    private List<ProcessInstanceResponseBody> processExtEntityToBody(List<ProcessExtEntity> processExtEntities){
        List<ProcessInstanceResponseBody> processInstanceResponseBodyList = new ArrayList<>();
        processExtEntities.forEach(item -> {
            ProcessInstanceResponseBody body = new ProcessInstanceResponseBody();
            BeanUtils.copyProperties(item,body);
            processInstanceResponseBodyList.add(body);
        });
        return processInstanceResponseBodyList;
    }
}
