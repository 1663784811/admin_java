package com.cyyaw.util.entity;


import java.io.Serializable;

/**
 * 查询模型
 * @author why
 */
public interface SelectModel extends Serializable {

    /**
     * 获取页码
     *
     * @return
     */
    int getPage();

    /**
     * 设置页码
     *
     * @param page
     */
    void setPage(int page);

    /**
     * 获取条数大小
     *
     * @return
     */
    int getSize();

    /**
     * 设置行数大小
     *
     * @param size
     */
    void setSize(int size);

    /**
     * 排序字符串
     */
    String getSort();

    void setSort(String sort);

    /**
     * 排序
     * 0 默认 ASC
     * 1 倒序 DESC
     */
    int getLine();

    void setLine(int line);
    
}
