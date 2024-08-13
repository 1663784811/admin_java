package com.cyyaw.web.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.web.table.entity.WebImage;


public interface WebImageService extends BaseTableService<WebImage, Integer> {

    WebImage findByTid(String tid);

}
