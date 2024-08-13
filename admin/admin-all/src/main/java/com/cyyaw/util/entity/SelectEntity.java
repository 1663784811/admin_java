package com.cyyaw.util.entity;

import lombok.Data;

/**
 * @author why
 */
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
    /**
     * 排序
     * 0 默认 ASC
     * 1 倒序 DESC
     */
    private int line = 0;
}
