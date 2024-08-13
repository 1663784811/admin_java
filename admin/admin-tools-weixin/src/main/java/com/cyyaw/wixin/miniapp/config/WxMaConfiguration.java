package com.cyyaw.wixin.miniapp.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.cyyaw.wixin.table.dao.WeixinMiniappSettingDao;
import com.cyyaw.wixin.table.entity.WeixinMiniappSetting;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;


@Slf4j
@Configuration
public class WxMaConfiguration {

    @Autowired
    private WeixinMiniappSettingDao weixinMiniappSettingDao;

    private final WxMaMessageHandler qrcodeHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            final File file = service.getQrcodeService().createQrcode("123", 430);
            WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
            service.getMsgService().sendKefuMsg(WxMaKefuMessage.newImageBuilder().mediaId(uploadResult.getMediaId()).toUser(wxMessage.getFromUser()).build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    };


    private final WxMaMessageHandler subscribeMsgHandler = (wxMessage, context, service, sessionManager) -> {
        service.getMsgService().sendSubscribeMsg(
                WxMaSubscribeMessage.builder()
                        .templateId("此处更换为自己的模板id")
                        .data(Lists.newArrayList(new WxMaSubscribeMessage.MsgData("keyword1", "339208499")))
                        .toUser(wxMessage.getFromUser())
                        .build()
        );
        return null;
    };

    private final WxMaMessageHandler logHandler = (wxMessage, context, service, sessionManager) -> {
        log.info("收到消息：" + wxMessage.toString());
        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMessage.toJson())
                .toUser(wxMessage.getFromUser()).build());
        return null;
    };

    private final WxMaMessageHandler textHandler = (wxMessage, context, service, sessionManager) -> {
        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("回复文本消息")
                .toUser(wxMessage.getFromUser()).build());
        return null;
    };

    private final WxMaMessageHandler picHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", "png", ClassLoader.getSystemResourceAsStream("tmp.png"));

            service.getMsgService().sendKefuMsg(
                    WxMaKefuMessage
                            .newImageBuilder()
                            .mediaId(uploadResult.getMediaId())
                            .toUser(wxMessage.getFromUser())
                            .build());

        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    };


    @Bean
    public WxMaService wxMaService() {
        log.info("---------   读取数据库数据,加载微信小程序模块");
        List<WeixinMiniappSetting> weixinMiniappSettingList = weixinMiniappSettingDao.findListByStatus(1);
        WxMaService maService = new WxMaServiceImpl();

        weixinMiniappSettingList.stream().map(obj -> {
            String miniAppId = obj.getMiniAppId();
            WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
            config.setAppid(obj.getMiniAppId());
            config.setSecret(obj.getSecret());
            config.setToken(obj.getToken());
            config.setAesKey(obj.getAesKey());
            config.setMsgDataFormat(obj.getMsgDataFormat());
            maService.addConfig(miniAppId, config);
            return obj;
        });

        return maService;
    }

    @Bean
    public WxMaMessageRouter wxMaMessageRouter(WxMaService wxMaService) {
        final WxMaMessageRouter router = new WxMaMessageRouter(wxMaService);
        router
                .rule().handler(logHandler).next()
                .rule().async(false).content("订阅消息").handler(subscribeMsgHandler).end()
                .rule().async(false).content("文本").handler(textHandler).end()
                .rule().async(false).content("图片").handler(picHandler).end()
                .rule().async(false).content("二维码").handler(qrcodeHandler).end();
        return router;
    }

}
