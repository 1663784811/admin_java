package com.cyyaw.jpa.util.entity;

import java.util.Date;

public interface BaseEntity<D> {

    D getId();

    void setId(D d);

    String getTid();

    void setTid(String tid);


    Date getCreateTime();

    void setCreateTime(Date createTime);

}
