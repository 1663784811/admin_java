package com.cyyaw.wixin.cp.config;

import com.cyyaw.wixin.cp.handler.*;
import com.cyyaw.wixin.table.dao.WeixinCpSettingDao;
import com.cyyaw.wixin.table.entity.WeixinCpSetting;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.constant.WxCpConsts;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Configuration
public class WxCpConfiguration {

    private LogCpAbstractHandler logHandler;
    private NullCpAbstractHandler nullHandler;
    private LocationCpAbstractHandler locationHandler;
    private MenuCpAbstractHandler menuHandler;
    private MsgCpAbstractHandler msgHandler;
    private ContactChangeCpAbstractHandler contactChangeHandler;
    private UnsubscribeCpAbstractHandler unsubscribeHandler;
    private SubscribeCpAbstractHandler subscribeHandler;

    private WeixinCpSettingDao weixinCpSettingDao;


    @Bean
    public WxCpService wxCpService() {
        log.info("---------   读取数据库数据,加载企业微信模块");
        // ================================  读取数据库
        List<WeixinCpSetting> weixinCpSettingList = weixinCpSettingDao.findListByStatus(1);
        WxCpServiceImpl service = new WxCpServiceImpl();


        weixinCpSettingList.stream().map(obj -> {


//            service.setWxCpConfigStorage();

            return obj;
        });


//        cpServices = properties.getAppConfigs().stream().map(a -> {


//            WxCpRedissonConfigImpl config = new WxCpRedissonConfigImpl(redissonClient, "workRedis:");
//            config.setCorpId(a.getCorpId());
//            config.setAgentId(a.getAgentId());
//            config.setCorpSecret(a.getSecret());
//            config.setToken(a.getToken());
//            config.setAesKey(a.getAesKey());
//
//            val service = new WxCpServiceImpl();
//            service.setWxCpConfigStorage(config);
//
//            routers.put(a.getCorpId() + a.getAgentId(), newRouter(service));
//
//
//            return service;
//        }).collect(
//                Collectors.toMap(
//                        service -> service.getWxCpConfigStorage().getCorpId() + service.getWxCpConfigStorage().getAgentId(), a -> a
//                )
//        );


        return service;
    }


    public WxCpMessageRouter newRouter(WxCpService wxCpService) {
        final val newRouter = new WxCpMessageRouter(wxCpService);
        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(logHandler).next();
        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.CLICK).handler(menuHandler).end();
        // 点击菜单链接事件（这里使用了一个空的处理器，可以根据自己需要进行扩展）
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.VIEW).handler(nullHandler).end();
        // 关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler).end();
        // 取消关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE).handler(unsubscribeHandler).end();
        // 上报地理位置事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.LOCATION).handler(locationHandler).end();
        // 接收地理位置消息
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).handler(locationHandler).end();
        // 扫码事件（这里使用了一个空的处理器，可以根据自己需要进行扩展）
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN).handler(nullHandler).end();
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxCpConsts.EventType.CHANGE_CONTACT).handler(contactChangeHandler).end();
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxCpConsts.EventType.ENTER_AGENT).handler(new EnterAgentCpAbstractHandler()).end();
        // 默认
        newRouter.rule().async(false).handler(msgHandler).end();
        return newRouter;
    }

}
