package cn.flow.component.taskShow.service;

import cn.flow.component.taskShow.dao.ProcessDefinitionTaskShowConfigEntity;
import cn.flow.component.taskShow.dao.ProcessInstanceTaskShowConfigDao;
import cn.flow.component.taskShow.dao.ProcessInstanceTaskShowConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstanceTaskShowConfigService {

    private final static Logger logger = LoggerFactory.getLogger(ProcessInstanceTaskShowConfigService.class);

    @Autowired
    private ProcessInstanceTaskShowConfigDao instanceTaskShowDao;

    @Autowired
    private ProcessDefinitionTaskShowConfigService definitionTaskShowConfigService;

    /**
     * 插入实例展示数据
     * 当流程任务定义了才插入
     */
    public void saveProcessInstanceTaskShow(String processInstanceId, String definitionTaskId, String instanceTaskId){
        ProcessDefinitionTaskShowConfigEntity definitionTaskShowConfigEntity =  definitionTaskShowConfigService.searchProcessDefinitionTaskShowConfigEntity(definitionTaskId);
        if (definitionTaskShowConfigEntity != null) {
            ProcessInstanceTaskShowConfigEntity entity = new ProcessInstanceTaskShowConfigEntity();

            entity.setProcessInstanceId(processInstanceId);
            entity.setInstanceTaskId(instanceTaskId);
            entity.setProcessKey(definitionTaskShowConfigEntity.getProcessKey());
            entity.setDefinitionTaskId(definitionTaskShowConfigEntity.getDefinitionTaskId());
            entity.setDisplayIndex(definitionTaskShowConfigEntity.getDisplayIndex());
            entity.setDisplay(definitionTaskShowConfigEntity.getDisplay());

            this.instanceTaskShowDao.save(entity);
        }
    }

    /**
     * 判断实例任务是否展示
     * 考虑历史任务实例情况，没有查询到返回true
     */
    public Boolean judgeTaskIsShow(String instanceTaskId) {
        ProcessInstanceTaskShowConfigEntity entity = searchProcessInstanceTaskShowConfigEntity(instanceTaskId);
        if (entity == null) {
            return true;
        }
        if (entity.getDisplay() == null) {
            return false;
        }
        return entity.getDisplay();
    }

    public void updateProcessInstanceTaskShowConfigEntity(String instanceTaskId, Boolean display) {
        ProcessInstanceTaskShowConfigEntity entity = searchProcessInstanceTaskShowConfigEntity(instanceTaskId);
        entity.setDisplay(display);
        instanceTaskShowDao.save(entity);
    }

    private ProcessInstanceTaskShowConfigEntity searchProcessInstanceTaskShowConfigEntity(String instanceTaskId) {
        return instanceTaskShowDao.findByInstanceTaskId(instanceTaskId);
    }



}
