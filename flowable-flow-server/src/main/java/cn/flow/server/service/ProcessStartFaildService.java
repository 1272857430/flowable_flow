package cn.flow.server.service;

import cn.flow.api.request.process.StartProcessInstanceRequestBody;
import cn.flow.server.service.dao.ProcessStartFaildDao;
import cn.flow.server.service.entity.ProcessStartFaildEntity;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProcessStartFaildService {

    private final static Logger logger = LoggerFactory.getLogger(ProcessStartFaildService.class);

    @Autowired
    private ProcessStartFaildDao processStartFaildDao;


    public void insertData(StartProcessInstanceRequestBody requestBody, String failReason){
        ProcessStartFaildEntity processStartFaildEntity = new ProcessStartFaildEntity();
        processStartFaildEntity.setProcessKey(requestBody.getProcessBusinessKey());
        processStartFaildEntity.setProcessName(requestBody.getProcessInstanceName());
        processStartFaildEntity.setStartUserId(requestBody.getInitiator());
        processStartFaildEntity.setFailReason(failReason);
        processStartFaildEntity.setStartParam(JSON.toJSONString(requestBody));
        processStartFaildEntity.setStartTime(new Date());
        processStartFaildDao.save(processStartFaildEntity);
    }
}
