package com.cyyaw.activiti.service;


import com.cyyaw.activiti.table.dto.ModelParam;
import com.cyyaw.util.tools.BaseResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface WorkerService {

    /**
     * 流程管理列表
     */
    BaseResult<Object> queryModeList(String key, String name, Integer pageSize, Integer pageNum);


    BaseResult<Object> queryModelById(String modelId) throws IOException;



    /**
     * 添加模
     */
    BaseResult<Object> addModel(ModelParam modelRequest) throws JsonProcessingException;

    void updateModel();


    BaseResult delModel(String modelId);


}
