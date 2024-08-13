package com.cyyaw.jpa.util.entity;


import cn.hutool.json.JSONArray;
import lombok.Data;

@Data
public class CommonSaveData {

    private String table;

    private JSONArray data;

}
