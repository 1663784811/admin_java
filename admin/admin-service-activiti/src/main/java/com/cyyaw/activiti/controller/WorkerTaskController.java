package com.cyyaw.activiti.controller;


import cn.hutool.core.util.StrUtil;
import com.cyyaw.activiti.service.WorkerService;
import com.cyyaw.activiti.table.dto.TaskInfo;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Api(tags = "工作流-待办")
@RestController
@RequestMapping("/admin/{eCode}/worker/task")
public class WorkerTaskController {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;


    @ApiOperation("查询所有待办任务列表")
    @GetMapping("/allTask")
    public BaseResult allTask(
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size
    ) {
        TaskQuery condition = taskService.createTaskQuery();
        return getTask(condition, key, name, page, size);
    }


    /**
     * 查询我的待办任务列表
     */
    @ApiOperation("查询我的待办任务列表")
    @PostMapping("/myTask")
    @ResponseBody
    public BaseResult myTask(
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size
    ) {
        String username = "";
        TaskQuery condition = taskService.createTaskQuery().taskAssignee(username);
        return getTask(condition, key, name, page, size);
    }


    private BaseResult getTask(TaskQuery condition, String key, String name, Integer page, Integer size) {
        if (StrUtil.isNotEmpty(name)) {
            condition.taskName(name);
        }
        if (StrUtil.isNotEmpty(key)) {
            condition.processDefinitionName(key);
        }
        // 过滤掉流程挂起的待办任务
        int total = condition.active().orderByTaskCreateTime().desc().list().size();
        List<Task> taskList = condition.active().orderByTaskCreateTime().desc().listPage((page - 1) * size, size);
        List<TaskInfo> tasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskList.stream().forEach(a -> {
            ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(a.getProcessInstanceId()).singleResult();
            TaskInfo info = new TaskInfo();
            info.setAssignee(a.getAssignee());
            info.setBusinessKey(process.getBusinessKey());
            info.setCreateTime(sdf.format(a.getCreateTime()));
            info.setTaskName(a.getName());
            info.setExecutionId(a.getExecutionId());
            info.setProcessInstanceId(a.getProcessInstanceId());
            info.setProcessName(process.getProcessDefinitionName());
            info.setStarter(process.getStartUserId());
            info.setStartTime(sdf.format(process.getStartTime()));
            info.setTaskId(a.getId());
//            String formKey = formService.getTaskFormData(a.getId()).getFormKey();
//            info.setFormKey(formKey);
            tasks.add(info);
        });
        PageRespone pageRespone = new PageRespone();
        pageRespone.setContent(tasks);
        pageRespone.setTotal((long) total);
        pageRespone.setPage(page);
        pageRespone.setSize(size);
        pageRespone.setTotalPage(page);
        return BaseResult.ok(pageRespone);
    }
}
