package com.cyyaw.activiti.service.impl;

import com.cyyaw.activiti.service.WorkerTaskMeetingService;
import com.cyyaw.activiti.table.dao.WorkerTaskMeetingDao;
import com.cyyaw.activiti.table.entity.WorkerTaskMeeting;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class WorkerTaskMeetingServiceImpl extends BaseService<WorkerTaskMeeting, Integer> implements WorkerTaskMeetingService {

    @Autowired
    private WorkerTaskMeetingDao workerTaskMeetingDao;

    @Override
    public BaseDao getBaseDao() {
        return workerTaskMeetingDao;
    }

}

