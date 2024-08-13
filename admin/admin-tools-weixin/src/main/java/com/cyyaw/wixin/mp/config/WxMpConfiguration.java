package com.cyyaw.wixin.mp.config;


import com.cyyaw.wixin.mp.handler.*;
import com.cyyaw.wixin.table.dao.WeixinMpSettingDao;
import com.cyyaw.wixin.table.entity.WeixinMpSetting;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.CustomerService.*;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.POI_CHECK_NOTIFY;

@Slf4j
@AllArgsConstructor
@Configuration
public class WxMpConfiguration {


    private final LogHandlerWxMp logHandler;
    private final NullHandlerWxMp nullHandler;
    private final KfSessionHandlerWxMp kfSessionHandler;
    private final StoreCheckNotifyHandlerWxMp storeCheckNotifyHandler;
    private final LocationHandlerWxMp locationHandler;
    private final MenuHandlerWxMp menuHandler;
    private final MsgHandlerWxMp msgHandler;
    private final UnsubscribeHandlerWxMp unsubscribeHandler;
    private final SubscribeHandlerWxMp subscribeHandler;
    private final ScanHandlerWxMp scanHandler;

    // =====================
    private final WeixinMpSettingDao weixinMpSettingDao;

    @Bean
    public WxMpService wxMpService() {
        log.info("---------   读取数据库数据,加载微信公众号模块");
        WxMpService service = new WxMpServiceImpl();
        // ================================  读取数据库
        List<WeixinMpSetting> weixinMpSettingList = weixinMpSettingDao.findListByStatus(1);
        // ================================
        weixinMpSettingList.stream().map(obj -> {
            String mpAppId = obj.getMpAppId();
            WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
            configStorage.setAppId(obj.getMpAppId());
            configStorage.setSecret(obj.getSecret());
            configStorage.setToken(obj.getToken());
            configStorage.setAesKey(obj.getAesKey());
            service.addConfigStorage(mpAppId, configStorage);
            return obj;
        });
        return service;
    }

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();
        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION).handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(EVENT).event(KF_CLOSE_SESSION).handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(EVENT).event(KF_SWITCH_SESSION).handler(this.kfSessionHandler).end();
        // 门店审核事件
        newRouter.rule().async(false).msgType(EVENT).event(POI_CHECK_NOTIFY).handler(this.storeCheckNotifyHandler).end();
        // 自定义菜单事件
        newRouter.rule().async(false).msgType(EVENT).event(EventType.CLICK).handler(this.menuHandler).end();
        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(EVENT).event(EventType.VIEW).handler(this.nullHandler).end();
        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();
        // 取消关注事件
        newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeHandler).end();
        // 上报地理位置事件
        newRouter.rule().async(false).msgType(EVENT).event(EventType.LOCATION).handler(this.locationHandler).end();
        // 接收地理位置消息
        newRouter.rule().async(false).msgType(XmlMsgType.LOCATION).handler(this.locationHandler).end();
        // 扫码事件
        newRouter.rule().async(false).msgType(EVENT).event(EventType.SCAN).handler(this.scanHandler).end();
        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();
        return newRouter;
    }

}
