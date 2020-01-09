package cn.flow.component.taskShow.service;

import cn.flow.component.taskShow.dao.ProcessDefinitionTaskShowConfigDao;
import cn.flow.component.taskShow.dao.ProcessDefinitionTaskShowConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProcessDefinitionTaskShowConfigService {

    public static final String SUFFIX_DISPLAY_INDEX = "-display-index";
    public static final String SUFFIX_DISPLAYABLE = "-displayable";

    private final static Logger logger = LoggerFactory.getLogger(ProcessDefinitionTaskShowConfigService.class);

    @Autowired
    private ProcessDefinitionTaskShowConfigDao definitionTaskShowDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 插入任务定义展示
     */
    public void insertProcessDefinitionTaskShow(String processKey, String definitionTaskId, int displayIndex, Boolean display) {
        // 删除旧的展示定义，在同步redis里的数据的时候需要出处理
        List<ProcessDefinitionTaskShowConfigEntity> entityList = searchProcessDefinitionTaskShowConfigEntityList(definitionTaskId);
        if (!CollectionUtils.isEmpty(entityList)) {
            entityList.forEach(entity -> definitionTaskShowDao.delete(entity.getId()));
        }
        // 插入新的展示定义
        ProcessDefinitionTaskShowConfigEntity entity = new ProcessDefinitionTaskShowConfigEntity();
        entity.setProcessKey(processKey);
        entity.setDefinitionTaskId(definitionTaskId);
        entity.setDisplayIndex(displayIndex);
        entity.setDisplay(display);
        definitionTaskShowDao.save(entity);
    }

    /**
     * 查询任务定义展示
     */
    public ProcessDefinitionTaskShowConfigEntity searchProcessDefinitionTaskShowConfigEntity(String definitionTaskId) {
        List<ProcessDefinitionTaskShowConfigEntity> entityList = searchProcessDefinitionTaskShowConfigEntityList(definitionTaskId);
        if (CollectionUtils.isEmpty(entityList))
            return null;
        return entityList.get(0);
    }

    /**
     * 查询任务定义展示
     */
    private List<ProcessDefinitionTaskShowConfigEntity> searchProcessDefinitionTaskShowConfigEntityList(String definitionTaskId) {
        return definitionTaskShowDao.findByDefinitionTaskId(definitionTaskId);
    }

    /**
     * 同步redis里的数据
     */
    public void syncRedisDefinitionShow() {
        Set<String> keys = stringRedisTemplate.keys("sid-*");
        Map<String, ProcessDefinitionTaskShowConfigEntity> map = new HashMap<>();
        for (String key : keys) {
            if (!key.startsWith("sid")) {
                continue;
            }
            String mapKey;
            if (key.contains(SUFFIX_DISPLAY_INDEX)) {
                mapKey = key.replace(SUFFIX_DISPLAY_INDEX, "");
            } else {
                mapKey = key.replace(SUFFIX_DISPLAYABLE, "");
            }
            if (!map.containsKey(mapKey)) {
                map.put(mapKey, new ProcessDefinitionTaskShowConfigEntity());
            }
            ProcessDefinitionTaskShowConfigEntity entity = map.get(mapKey);
            Object value = stringRedisTemplate.opsForValue().get(key);
            if (key.contains(SUFFIX_DISPLAY_INDEX)) {
                entity.setDisplayIndex(Integer.valueOf(String.valueOf(value)));
            } else {
                entity.setDisplay(false);
                if ("true".equalsIgnoreCase(String.valueOf(value)))
                    entity.setDisplay(true);
            }
            map.put(mapKey, entity);
        }
        map.forEach((key, entity) -> insertProcessDefinitionTaskShow("redisSync", key, entity.getDisplayIndex(), entity.getDisplay()));
    }
}
