package com.cyyaw.activiti.controller.task;

import com.cyyaw.activiti.table.entity.WorkerTaskMeeting;
import io.swagger.annotations.Api;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Api(tags = "工作流-发起会议")
@RestController
@RequestMapping("/admin/{eCode}/worker/meeting")
public class MeetingController {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    /**
     * 新增保存会议
     */
    @PostMapping("/addMeeting")
    public void addMeeting(WorkerTaskMeeting workerTaskMeeting) {
//        return toAjax(meetingService.insertMeeting(workerTaskMeeting));

    }


}
