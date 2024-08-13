package com.cyyaw.activiti.controller;

import com.cyyaw.activiti.service.WorkerTaskMeetingService;
import com.cyyaw.activiti.table.entity.WorkerTaskMeeting;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@RequestMapping("/admin/{eCode}/worker/meeting")
@RestController
public class WorkerTaskMeetingController {

    @Autowired
    private WorkerTaskMeetingService workerTaskMeetingService;

//    @Resource
//    IdentityService identityService;

    @Resource
    private RuntimeService runtimeService;


    /**
     * 添加或修改
     */
    @PostMapping("/saveWorkerTaskMeeting")
    public BaseResult saveWorkerTaskMeeting(@RequestBody WorkerTaskMeeting saveObj) {
        WorkerTaskMeeting obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
             saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = workerTaskMeetingService.save(saveObj);


            // 启动会议流程
//            identityService.setAuthenticatedUserId(obj.getHost());
//            HashMap<String, Object> variables = new HashMap<>();
//            variables.put("host", obj.getHost());
//            String[] person = obj.getPeoplelist().split(",");
//            variables.put("people", Arrays.asList(person));
//            runtimeService.startProcessInstanceByKey("meeting", String.valueOf(obj.getId()), variables);


        } else {
            //修改
            log.info("修改:{}", saveObj);
            WorkerTaskMeeting cpObj = workerTaskMeetingService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj,cpObj);
            obj = workerTaskMeetingService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

}
