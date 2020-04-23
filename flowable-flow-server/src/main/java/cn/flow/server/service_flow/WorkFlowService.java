package cn.flow.server.service_flow;

import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.component.taskShow.dao.ProcessDefinitionTaskShowConfigEntity;
import cn.flow.component.taskShow.service.ProcessDefinitionTaskShowConfigService;
import cn.flow.component.taskShow.service.ProcessInstanceTaskShowConfigService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkFlowService {

    @Autowired
    private WorkFlowFormService flowFormService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessDefinitionTaskShowConfigService definitionTaskShowConfigService;

    @Autowired
    private ProcessInstanceTaskShowConfigService instanceTaskShowConfigService;

    /**
     * 获取流程已处理表单数据
     */
    public List<FormModelResponseBody> getProcessedFormData(String processInstanceId) {
        List<FormModelResponseBody>  taskFormDatas = new ArrayList<>();
        // 启动表单信息
        FormModelResponseBody startFormModel = flowFormService.getStartFormModelByInstanceId(processInstanceId);
        taskFormDatas.add(startFormModel);

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .orderByTaskCreateTime().asc()
                .processInstanceId(processInstanceId).list();

        Map<Integer, FormModelResponseBody> forms = new HashMap<>();

        for (HistoricTaskInstance ele : historicTaskInstances) {
            if (Objects.isNull(ele))
                continue;
            ProcessDefinitionTaskShowConfigEntity definitionTaskShowConfigEntity = definitionTaskShowConfigService.searchProcessDefinitionTaskShowConfigEntity(ele.getTaskDefinitionKey());
            // 任务定义展示
            if (!Objects.isNull(definitionTaskShowConfigEntity) && definitionTaskShowConfigEntity.getDisplay()) {
                // 任务实例展示
                if (instanceTaskShowConfigService.judgeTaskIsShow(ele.getId())) {
                    FormModelResponseBody taskFormData = flowFormService.getTaskFormData(ele.getId(), ele.getName());
                    if (!Objects.isNull(taskFormData)) {
                        forms.put(definitionTaskShowConfigEntity.getDisplayIndex(), taskFormData);
                    }
                }
            }
        }
        forms.forEach((key ,value) -> taskFormDatas.add(value));
        return taskFormDatas;
    }

    public Map<String, Object> getProcessVariables(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .includeProcessVariables().singleResult();
        return processInstance.getProcessVariables();
    }
}
