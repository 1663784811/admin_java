package com.cyyaw.util.entity;


import com.cyyaw.content.table.entity.CttContent;
import com.cyyaw.user.table.entity.UUser;
import lombok.Data;

@Data
public class ContentResponse {

    private String tid;

    private String userName;

    private String userId;

    private String face;

    private String contentText;




    /**
     * 用户
     */
    private UUser user;

    /**
     * 内容
     */
    private CttContent content;


}
