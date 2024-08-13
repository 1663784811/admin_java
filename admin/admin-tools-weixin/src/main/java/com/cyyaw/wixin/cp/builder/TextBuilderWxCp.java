package com.cyyaw.wixin.cp.builder;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutTextMessage;


public class TextBuilderWxCp extends WxCpAbstractBuilder {

    @Override
    public WxCpXmlOutMessage build(String content, WxCpXmlMessage wxMessage, WxCpService service) {
        WxCpXmlOutTextMessage m = WxCpXmlOutMessage.TEXT().content(content).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
        return m;
    }

}
