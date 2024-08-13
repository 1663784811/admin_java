package com.cyyaw.wixin.mp.controller;

import com.cyyaw.util.tools.BaseResult;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/wx/mp/jsapi/{mpAppId}")
public class WxMpJsapiController {

    private final WxMpService wxService;

    @GetMapping("/getJsapiTicket")
    public BaseResult getJsapiTicket(
            @PathVariable String mpAppId,
            @PathVariable String url
    ) throws WxErrorException {
        WxMpService wxMpService = wxService.switchoverTo(mpAppId);
        WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(url);
        return BaseResult.ok(jsapiSignature);
    }
}
