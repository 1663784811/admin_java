package com.cyyaw.jpa.util.entity;

import lombok.Data;

@Data
public class SelectEntity implements SelectModel {
    /**
     * 页码
     */
    private int page = 1;
    /**
     * 大小
     */
    private int size = 30;
    /**
     * 排序   sort格式：    字段_desc,字段_asc
     */
    private String sort;

}
