package com.cyyaw.jpa;

import cn.hutool.json.JSONObject;
import com.cyyaw.util.tools.PageRespone;

import java.util.List;

public interface BaseTableService<T, D> {

    List<T> findAll(JSONObject json);

    List<T> findByExample(T t);

    PageRespone<T> findPage(JSONObject json);

    T findId(D id);

    List<T> findTree(JSONObject json);

    T saveTree(T t);

    void delTree(D id);

    T save(T t);

    void del(D... idArr);




}
