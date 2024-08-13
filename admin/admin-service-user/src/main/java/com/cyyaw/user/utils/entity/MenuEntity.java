package com.cyyaw.user.utils.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树对象
 */
@Data
public class MenuEntity implements Serializable {


    private String title;

    private String icon;

    private String routeName;

    private String url;

    private Params params;

    private List<MenuEntity> children;


    @Data
    public static class Params {
        private String appId;
    }

}
