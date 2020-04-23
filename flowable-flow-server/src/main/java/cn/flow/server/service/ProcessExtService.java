package cn.flow.server.service;

import cn.flow.api.enums.ProcessStatus;
import cn.flow.api.request.process.GetProcessInstanceRequestBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.server.service.dao.ProcessExtDao;
import cn.flow.server.service.entity.ProcessExtEntity;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

@Slf4j
@Service
public class ProcessExtService {

    @Autowired
    private ProcessExtDao processExtDao;

    @Autowired
    private HistoryService historyService;

    /**
     * 保存流程实例
     */
    public ProcessInstanceResponseBody saveProcessInstance(ProcessInstance processInstance, String processScopeId){
        // TODO 此处需要接入用户服务
        String startUserName = "系统用户";
        ProcessExtEntity processExtEntity = new ProcessExtEntity(processInstance, processScopeId, startUserName, ProcessStatus.UNFINISHED.getCode());
        processExtDao.save(processExtEntity);
        ProcessInstanceResponseBody processInstanceResponseBody = new ProcessInstanceResponseBody();
        BeanUtils.copyProperties(processExtEntity, processInstanceResponseBody);
        return processInstanceResponseBody;
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
                Date date = new Date(requestBody.getStartTime());
                andList.add(cb.greaterThanOrEqualTo(startTimePath.as(Date.class), date));
            }
            // 结束时间
            if (!Objects.isNull(requestBody.getEndTime())){
                Date date = new Date(requestBody.getEndTime());
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
