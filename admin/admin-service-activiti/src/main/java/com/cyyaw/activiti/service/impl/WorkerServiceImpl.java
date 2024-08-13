package com.cyyaw.activiti.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cyyaw.activiti.service.WorkerService;
import com.cyyaw.activiti.table.dto.ModelParam;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 流程管理列表
     *
     * @return
     */
    @Override
    public BaseResult<Object> queryModeList(String key, String name, Integer pageSize, Integer pageNum) {
        ModelQuery query = repositoryService.createModelQuery();
        if (StrUtil.isNotEmpty(key)) {
            query.modelKey(key);
        }
        if (StrUtil.isNotEmpty(name)) {
            query.modelName(name);
        }
        int start = (pageNum - 1) * pageSize;
        List<Model> list = query.orderByCreateTime().desc().listPage(start, pageSize);
        PageRespone pageRespone = new PageRespone();
        pageRespone.setContent(list);
        pageRespone.setTotal((long) query.list().size());
        pageRespone.setPage(pageNum);
        pageRespone.setSize(pageSize);
        pageRespone.setTotalPage(pageNum);
        return BaseResult.ok(pageRespone);
    }

    @Override
    public BaseResult<Object> queryModelById(String modelId) throws IOException {
        Model model = repositoryService.getModel(modelId);
        byte[] modelData = repositoryService.getModelEditorSource(modelId);
        JsonNode jsonNode = objectMapper.readTree(modelData);
        BpmnModel bpmnModel = (new BpmnJsonConverter()).convertToBpmnModel(jsonNode);
        Deployment deploy = repositoryService.createDeployment().category(model.getCategory()).name(model.getName()).key(model.getKey()).addBpmnModel(model.getKey() + ".bpmn20.xml", bpmnModel).deploy();
        model.setDeploymentId(deploy.getId());
        repositoryService.saveModel(model);
        return BaseResult.ok();
    }


    @Override
    public BaseResult<Object> addModel(ModelParam modelRequest) throws JsonProcessingException {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        List<Model> list = modelQuery.modelKey(modelRequest.getKey()).list();
        if (list.isEmpty()) {
            Model model = repositoryService.newModel();
            model.setCategory(modelRequest.getCategory());
            model.setKey(modelRequest.getKey());
            ObjectNode modelNode = objectMapper.createObjectNode();
            modelNode.put(ModelDataJsonConstants.MODEL_NAME, modelRequest.getName());
            modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, modelRequest.getDescription());
            modelNode.put(ModelDataJsonConstants.MODEL_REVISION, modelRequest.getVersion());
            model.setMetaInfo(modelNode.toString());
            model.setName(modelRequest.getName());
            model.setVersion(modelRequest.getVersion());
            // 保存模型到act_re_model表
            repositoryService.saveModel(model);
            HashMap<String, Object> content = new HashMap();
            content.put("resourceId", model.getId());
            HashMap<String, String> properties = new HashMap();
            properties.put("process_id", modelRequest.getKey());
            properties.put("name", modelRequest.getName());
            properties.put("category", modelRequest.getCategory());
            content.put("properties", properties);
            HashMap<String, String> stencilset = new HashMap();
            stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            content.put("stencilset", stencilset);
            // 保存模型文件到act_ge_bytearray表
            repositoryService.addModelEditorSource(model.getId(), objectMapper.writeValueAsBytes(content));
            return BaseResult.ok(model);
        } else {
            return BaseResult.fail("模型ID已存在");
        }
    }

    @Override
    public void updateModel() {

    }

    @Override
    public BaseResult delModel(String modelId) {
        repositoryService.deleteModel(modelId);
        return BaseResult.ok("删除成功");
    }


}
