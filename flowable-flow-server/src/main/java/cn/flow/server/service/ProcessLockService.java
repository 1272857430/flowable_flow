package cn.flow.server.service;

import cn.flow.server.service.dao.ProcessLockDao;
import cn.flow.server.service.entity.ProcessLockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class ProcessLockService {

    private final static Logger logger = LoggerFactory.getLogger(ProcessLockService.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private ProcessLockDao processLockDao;

    private ProcessLockEntity getByProcessKey(String processKey) {
        List<ProcessLockEntity> list = processLockDao.findByProcessKey(processKey);
        if (!CollectionUtils.isEmpty(list))
            return list.get(0);
        return null;
    }

    /**
     * 给流程加锁
     */
    public void tryLockProcess(String processKey, String processScopeId, String StartUserId) {
        ProcessLockEntity processLockEntity = getByProcessKey(processKey);
        if (Objects.isNull(processLockEntity) || "false".equalsIgnoreCase(processLockEntity.getIsLock())){
            logger.info("流程:{}不需要加锁", processKey);
            return;
        }
        logger.info("给{}({})流程加锁", processLockEntity.getProcessKey(), processLockEntity.getProcessName());
        String redisKey = processKey + "_" + processScopeId + "_" + StartUserId;
        boolean lockResult = redisService.saveLock(redisKey, processLockEntity.getLockTime());
        if (!lockResult){
            throw new RuntimeException("请您不要重复发起" + processLockEntity.getProcessName());
        }
    }

    /**
     * 给流程释放锁
     */
    public void releaseProcessLock(String processKey, String processScopeId, String StartUserId) {
        logger.info("给{}流程释放锁", processKey);
        String redisKey = processKey + "_" + processScopeId + "_" + StartUserId;
        redisService.releaseLock(redisKey);
    }
}
