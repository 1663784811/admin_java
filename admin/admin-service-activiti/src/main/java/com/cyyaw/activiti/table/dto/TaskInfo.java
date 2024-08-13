package com.cyyaw.activiti.table.dto;

import lombok.Data;

@Data
public class TaskInfo {
    private String taskId;
    private String processInstanceId;
    private String executionId;
    private String businessKey;
    private String processName;
    private String taskName;
    private String starter;
    private String assignee;
    private String startTime;
    private String endTime;
    private String createTime;
    private String formKey;
    private String comment;
    private Integer pageSize;
    private Integer pageNum;
}
