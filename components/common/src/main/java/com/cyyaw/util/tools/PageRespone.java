package com.cyyaw.util.tools;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageRespone<T> implements Serializable {
    /**
     * 列表数据
     */
    private List<T> content;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 大小
     */
    private Integer size;

    /**
     * 总页数
     */
    private Integer totalPage;
}
