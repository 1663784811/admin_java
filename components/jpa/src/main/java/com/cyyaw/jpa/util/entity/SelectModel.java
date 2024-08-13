package com.cyyaw.jpa.util.entity;


/**
 * 查询模型
 */
public interface SelectModel {

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

    /**
     * 设置排序
     */
    void setSort(String sort);

}
