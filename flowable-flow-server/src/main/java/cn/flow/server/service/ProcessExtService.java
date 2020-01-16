package cn.flow.server.service;

import cn.flow.api.enums.ProcessStatus;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.server.service.dao.ProcessExtDao;
import cn.flow.server.service.entity.ProcessExtEntity;
import cn.flow.server.utils.DateUtil;
import cn.flow.server.utils.RandomNumberUtil;
import cn.flow.server.utils.StringHelper;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

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
}
