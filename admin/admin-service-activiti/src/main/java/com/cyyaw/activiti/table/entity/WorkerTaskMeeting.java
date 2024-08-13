package com.cyyaw.activiti.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "worker_task_meeting")
@org.hibernate.annotations.Table(appliesTo = "worker_task_meeting", comment = "工作流会议申请")
public class WorkerTaskMeeting  implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    @Column(name = "id", columnDefinition = "int auto_increment not null COMMENT 'id'")
    private Integer id;
    @Basic
    @Column(name = "tid", columnDefinition = "varchar(32) unique not null default '' COMMENT 'tid'")
    private String tid;
    @Basic
    @Column(name = "create_time", columnDefinition = "datetime default now() COMMENT '创建时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Basic
    @Column(name = "del", columnDefinition = "int default '0' COMMENT '是否删除{0:否,1:是}'")
    private Integer del;
    @Basic
    @Column(name = "note", columnDefinition = "varchar(255) default '' COMMENT '备注'")
    private String note;

    // =================================================================================
    @Basic
    @Column(name = "enterprise_code", columnDefinition = "varchar(32) COMMENT '所属企业e_enterprise表code'")
    private String enterpriseCode;

    @Basic
    @Column(name = "topic", columnDefinition = "varchar(255) COMMENT '会议主题'")
    private String topic;

    @Basic
    @Column(name = "host", columnDefinition = "varchar(255) COMMENT '主持人'")
    private String host;

    @Basic
    @Column(name = "place", columnDefinition = "varchar(255) COMMENT '会议地址'")
    private String place;

    @Basic
    @Column(name = "people_list", columnDefinition = "text COMMENT '参会人员'")
    private String peopleList;

    @Basic
    @Column(name = "start_time", columnDefinition = "datetime default now() COMMENT '开始时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Basic
    @Column(name = "end_time", columnDefinition = "datetime COMMENT '结束时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Basic
    @Column(name = "content", columnDefinition = "text COMMENT '会议纪要'")
    private String content;

}
