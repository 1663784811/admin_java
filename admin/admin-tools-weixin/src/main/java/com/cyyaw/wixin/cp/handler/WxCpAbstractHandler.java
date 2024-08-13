package com.cyyaw.wixin.cp.handler;

import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class WxCpAbstractHandler implements WxCpMessageHandler {
  protected Logger logger = LoggerFactory.getLogger(getClass());
}
