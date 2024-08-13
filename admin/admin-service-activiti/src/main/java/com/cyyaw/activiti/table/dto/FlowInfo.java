package com.cyyaw.activiti.table.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FlowInfo {
    String processInstanceId;
    String businessKey;
    String name;
    Boolean suspended;
    Boolean ended;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;
    String startUserId;
    // 当前节点
    String currentTask;
    // 当前办理人
    String assignee;
}
