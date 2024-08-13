package com.cyyaw.service;

import com.cyyaw.util.entity.ContentResponse;
import com.cyyaw.util.tools.BaseResult;

import java.util.List;

public interface ContentService {


    BaseResult<List<ContentResponse>> findContent(String appId, Integer page, Integer size);


}
