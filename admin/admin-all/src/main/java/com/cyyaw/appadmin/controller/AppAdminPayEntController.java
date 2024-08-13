package com.cyyaw.appadmin.controller;

import com.github.binarywang.wxpay.bean.entpay.*;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("微信-企业付款")
@RequestMapping("/appAdmin/{appId}/pay")
@RestController
public class AppAdminPayEntController {

    @Autowired
    private WxPayService wxService;

    @ApiOperation(value = "企业付款到零钱")
    @PostMapping("/entPay")
    public EntPayResult entPay(@RequestBody EntPayRequest request) throws WxPayException {
        return this.wxService.getEntPayService().entPay(request);
    }


    @ApiOperation(value = "查询企业付款到零钱的结果")
    @GetMapping("/queryEntPay/{partnerTradeNo}")
    public EntPayQueryResult queryEntPay(@PathVariable String partnerTradeNo) throws WxPayException {
        return this.wxService.getEntPayService().queryEntPay(partnerTradeNo);
    }

    @ApiOperation(value = "获取RSA加密公钥")
    @GetMapping("/getPublicKey")
    public String getPublicKey() throws WxPayException {
        return this.wxService.getEntPayService().getPublicKey();
    }

    @ApiOperation(value = "企业付款到银行卡")
    @PostMapping("/payBank")
    public EntPayBankResult payBank(EntPayBankRequest request) throws WxPayException {
        return this.wxService.getEntPayService().payBank(request);
    }

    @ApiOperation(value = "查询企业付款到银行卡的结果")
    @GetMapping("/queryPayBank/{partnerTradeNo}")
    public EntPayBankQueryResult queryPayBank(@PathVariable String partnerTradeNo) throws WxPayException {
        return this.wxService.getEntPayService().queryPayBank(partnerTradeNo);
    }

}
