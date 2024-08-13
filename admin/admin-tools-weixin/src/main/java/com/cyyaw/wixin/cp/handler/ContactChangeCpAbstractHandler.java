package com.cyyaw.wixin.cp.handler;

import com.cyyaw.wixin.cp.builder.TextBuilderWxCp;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ContactChangeCpAbstractHandler extends WxCpAbstractHandler {

  @Override
  public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService cpService, WxSessionManager sessionManager) {
    String content = "收到通讯录变更事件，内容：" + wxMessage;
    log.info(content);
    return new TextBuilderWxCp().build(content, wxMessage, cpService);
  }

}
