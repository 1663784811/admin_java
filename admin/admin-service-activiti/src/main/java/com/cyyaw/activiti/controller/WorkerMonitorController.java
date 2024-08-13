package com.cyyaw.activiti.controller;


import cn.hutool.core.util.StrUtil;
import com.cyyaw.activiti.table.dto.FlowInfo;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "工作流-流程监控")
@RestController
@RequestMapping("/admin/{eCode}/worker/monitor")
public class WorkerMonitorController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ProcessEngineConfiguration configuration;

//    @Autowired
//    private ActivitiTracingChart activitiTracingChart;

    //
//    @GetMapping("/historyDetail")
//    public String historyDetail(String processInstanceId, ModelMap mmap) {
//        mmap.put("processInstanceId", processInstanceId);
//        return prefix + "/processHistoryDetail";
//    }
//
//    @GetMapping("/processVariablesDetail")
//    public String processVariablesDetail(String processInstanceId, ModelMap mmap) {
//        mmap.put("processInstanceId", processInstanceId);
//        return prefix + "/processVariablesDetail";
//    }
//
    @ApiOperation("查询所有正在运行的流程实例列表")
    @GetMapping("/processList")
    public BaseResult processList(
            @RequestParam(required = false) String bussinesskey,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size
    ) {
        int start = (page - 1) * size;
        ProcessInstanceQuery condition = runtimeService.createProcessInstanceQuery();
        if (StrUtil.isNotEmpty(bussinesskey)) {
            condition.processInstanceBusinessKey(bussinesskey);
        }
        if (StrUtil.isNotEmpty(name)) {
            condition.processDefinitionName(name);
        }
        List<ProcessInstance> processList = condition.orderByProcessDefinitionId().desc().listPage(start, size);
        int total = condition.orderByProcessDefinitionId().desc().list().size();
        List<FlowInfo> flows = new ArrayList<>();
        processList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getProcessInstanceId());
            info.setBusinessKey(p.getBusinessKey());
            info.setName(p.getProcessDefinitionName());
            info.setStartTime(p.getStartTime());
            info.setStartUserId(p.getStartUserId());
            info.setSuspended(p.isSuspended());
            info.setEnded(p.isEnded());
            // 查看当前活动任务
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(p.getProcessInstanceId()).list();
            String taskName = "";
            String assignee = "";
            for (Task t : tasks) {
                taskName += t.getName() + ",";
                assignee += t.getAssignee() + ",";
            }
            taskName = taskName.substring(0, taskName.length() - 1);
            assignee = assignee.substring(0, assignee.length() - 1);
            info.setCurrentTask(taskName);
            info.setAssignee(assignee);
            flows.add(info);
        });
        PageRespone pageRespone = new PageRespone();
        pageRespone.setContent(flows);
        pageRespone.setTotal((long) total);
        pageRespone.setPage(page);
        pageRespone.setSize(size);
        pageRespone.setTotalPage(page);
        return BaseResult.ok(pageRespone);
    }

    @ApiOperation("查询所有流程实例列表-包含在运行和已结束")
    @GetMapping("/queryHistoryProcess")
    public BaseResult queryHistoryProcess(
            @RequestParam(required = false) String bussinesskey,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size
    ) {
        int total = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().desc().list().size();
        int start = (page - 1) * size;
        HistoricProcessInstanceQuery condition = historyService.createHistoricProcessInstanceQuery();
        if (StrUtil.isNotEmpty(bussinesskey)) {
            condition.processInstanceBusinessKey(bussinesskey);
        }
        if (StrUtil.isNotEmpty(name)) {
            condition.processDefinitionName(name);
        }
        List<HistoricProcessInstance> processList = condition.orderByProcessInstanceStartTime().desc().listPage(start, size);
        List<FlowInfo> flows = new ArrayList<>();
        processList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getId());
            info.setBusinessKey(p.getBusinessKey());
            info.setName(p.getProcessDefinitionName());
            info.setStartTime(p.getStartTime());
            info.setEndTime(p.getEndTime());
            info.setStartUserId(p.getStartUserId());
            if (p.getEndTime() == null) {
                info.setEnded(false);
                // 查看当前活动任务
                List<Task> tasks = taskService.createTaskQuery().processInstanceId(p.getId()).list();
                String taskName = "";
                String assignee = "";
                for (Task t : tasks) {
                    taskName += t.getName() + ",";
                    assignee += t.getAssignee() + ",";
                }
                taskName = taskName.substring(0, taskName.length() - 1);
                assignee = assignee.substring(0, assignee.length() - 1);
                info.setCurrentTask(taskName);
                info.setAssignee(assignee);
            } else {
                info.setEnded(true);
            }
            flows.add(info);
        });

        PageRespone pageRespone = new PageRespone();
        pageRespone.setContent(flows);
        pageRespone.setTotal((long) total);
        pageRespone.setPage(page);
        pageRespone.setSize(size);
        pageRespone.setTotalPage(page);
        return BaseResult.ok(pageRespone);
    }

//    @ApiOperation("查询一个流程的活动历史")
//    @RequestMapping(value = "/history/{processInstanceId}", method = RequestMethod.POST)
//    @ResponseBody
//    public TableDataInfo history(@PathVariable String processInstanceId, Integer pageSize, Integer pageNum) {
//        int start = (pageNum - 1) * pageSize;
//        List<HistoricActivityInstance> history = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").orderByHistoricActivityInstanceStartTime().asc().listPage(start, pageSize);
//        int total = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").orderByHistoricActivityInstanceStartTime().asc().list().size();
//        List<TaskInfo> infos  = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        history.stream().forEach(h->{
//            TaskInfo info = new TaskInfo();
//            info.setProcessInstanceId(h.getProcessInstanceId());
//            info.setStartTime(sdf.format(h.getStartTime()));
//            if (h.getEndTime() != null) {
//                info.setEndTime(sdf.format(h.getEndTime()));
//            }
//            info.setAssignee(h.getAssignee());
//            info.setTaskName(h.getActivityName());
//            List<Comment> comments = taskService.getTaskComments(h.getTaskId());
//            if (comments.size() > 0) {
//                info.setComment(comments.get(0).getFullMessage());
//            }
//            infos.add(info);
//        });
//        TableDataInfo rspData = new TableDataInfo();
//        rspData.setCode(0);
//        rspData.setRows(infos);
//        rspData.setTotal(total);
//        return rspData;
//    }
//
//    @ApiOperation("查询所有正在运行的执行实例列表")
//    @RequestMapping(value = "/listExecutions", method = RequestMethod.POST)
//    @ResponseBody
//    public TableDataInfo listExecutions(@RequestParam(required = false) String key, @RequestParam(required = false) String name,
//                                        Integer pageSize, Integer pageNum) {
//        int start = (pageNum - 1) * pageSize;
//        List<Execution> executionList = runtimeService.createExecutionQuery().orderByProcessInstanceId().desc().listPage(start, pageSize);
//        int total = runtimeService.createExecutionQuery().orderByProcessInstanceId().desc().list().size();
//        List<FlowInfo> flows = new ArrayList<>();
//        executionList.stream().forEach(p -> {
//            FlowInfo info = new FlowInfo();
//            info.setProcessInstanceId(p.getProcessInstanceId());
//            info.setSuspended(p.isSuspended());
//            info.setEnded(p.isEnded());
//            flows.add(info);
//        });
//        TableDataInfo rspData = new TableDataInfo();
//        rspData.setCode(0);
//        rspData.setRows(flows);
//        rspData.setTotal(total);
//        return rspData;
//    }
//
//    @ApiOperation("流程图进度追踪,已结束标红，运行中标绿")
//    @RequestMapping(value = {"/traceProcess/{processInstanceId}"}, method = RequestMethod.GET)
//    public void traceprocess(@PathVariable String processInstanceId, HttpServletResponse response) throws IOException {
//        activitiTracingChart.generateFlowChart(processInstanceId, response.getOutputStream());
//    }
//
//    @ApiOperation("挂起一个流程实例")
//    @RequestMapping(value = "/suspend/{processInstanceId}", method = RequestMethod.GET)
//    @ResponseBody
//    public AjaxResult suspend(@PathVariable String processInstanceId) {
//        runtimeService.suspendProcessInstanceById(processInstanceId);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation("唤醒一个挂起的流程实例")
//    @RequestMapping(value = "/run/{processInstanceId}", method = RequestMethod.GET)
//    @ResponseBody
//    public AjaxResult rerun(@PathVariable String processInstanceId) {
//        runtimeService.activateProcessInstanceById(processInstanceId);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation("查询一个流程的变量")
//    @RequestMapping(value = "/variables/{processInstanceId}", method = RequestMethod.POST)
//    @ResponseBody
//    public TableDataInfo variables(@PathVariable String processInstanceId, Integer pageSize, Integer pageNum) {
//        int start = (pageNum - 1) * pageSize;
//        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).orderByVariableName().asc().listPage(start, pageSize);
//        int total = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).orderByVariableName().asc().list().size();
//        List<VariableInfo> infos = new ArrayList<>();
//        variables.forEach(v->{
//            VariableInfo info = new VariableInfo();
//            BeanUtils.copyBeanProp(info, v);
//            info.setValue(v.getValue().toString());
//            infos.add(info);
//        });
//        TableDataInfo rspData = new TableDataInfo();
//        rspData.setCode(0);
//        rspData.setRows(infos);
//        rspData.setTotal(total);
//        return rspData;
//    }
}
