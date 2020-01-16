package cn.flow.server.service;

import cn.flow.server.service.dao.ProcessStartChanceDao;
import cn.flow.server.service.entity.ProcessStartChanceEntity;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProcessStartChanceService {

    private final static Logger logger = LoggerFactory.getLogger(ProcessStartChanceService.class);

    @Autowired
    private ProcessStartChanceDao processStartChanceDao;

    private ProcessStartChanceEntity searchProcessStartChance(String processKey, String processScopeId) {
        List<ProcessStartChanceEntity> list = processStartChanceDao.findByProcessKeyAndProcessScopeId(processKey, processScopeId);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            list = processStartChanceDao.findByProcessKeyAndProcessScopeId(processKey, "other");
            return CollectionUtils.isEmpty(list) ? null : list.get(0);
        }
    }

    /**
     * 判断流程是否需要发起
     */
    public boolean judgeStartProcess(String processKey, String processScopeId) {
        logger.info("判断流程是否需要发起processKey={}, processScopeId={}", processKey, processScopeId);
        ProcessStartChanceEntity processStartChanceEntity = searchProcessStartChance(processKey, processScopeId);
        if (processStartChanceEntity == null) {
            logger.info("流程：{}没有配置发起概率，本次发起流程", processKey);
            return true;
        }
        double chance = Math.random();
        logger.info("适用的发起概率规则为：" + JSON.toJSONString(processStartChanceEntity));
        logger.info("本次的发起概率为{}", chance);
        if (processStartChanceEntity.getStartChance() == null ){
            logger.info("流程：{}({})没有配置发起概率，本次发起流程", processKey, processStartChanceEntity.getProcessName());
            return true;
        }
        if (processStartChanceEntity.getStartChance() >= 1.0 || chance < processStartChanceEntity.getStartChance()) {
            logger.info("流程:{}({}),发起概率为:{}，本次发起流程", processStartChanceEntity.getProcessKey(), processStartChanceEntity.getProcessName(), processStartChanceEntity.getStartChance());
            return true;
        }
        logger.info("流程:{}({}),发起概率为:{}，本次不发起流程", processStartChanceEntity.getProcessKey(), processStartChanceEntity.getProcessName(), processStartChanceEntity.getStartChance());
        return false;
    }
}
