package com.cyyaw.appadmin.controller;

import com.github.binarywang.wxpay.bean.coupon.*;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxScanPayNotifyResult;
import com.github.binarywang.wxpay.bean.request.*;
import com.github.binarywang.wxpay.bean.result.*;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;


@Api("微信支付")
@RestController
@RequestMapping("/appAdmin/{appId}/pay/wx")
public class WxPayController {

    @Autowired
    private WxPayService wxService;


    @ApiOperation(value = "查询订单")
    @GetMapping("/queryOrder")
    public WxPayOrderQueryResult queryOrder(@RequestParam(required = false) String transactionId, @RequestParam(required = false) String outTradeNo) throws WxPayException {
        return wxService.queryOrder(transactionId, outTradeNo);
    }

    @ApiOperation(value = "查询订单")
    @PostMapping("/queryOrder")
    public WxPayOrderQueryResult queryOrder(@RequestBody WxPayOrderQueryRequest wxPayOrderQueryRequest) throws WxPayException {
        return wxService.queryOrder(wxPayOrderQueryRequest);
    }

    @ApiOperation(value = "关闭订单")
    @GetMapping("/closeOrder/{outTradeNo}")
    public WxPayOrderCloseResult closeOrder(@PathVariable String outTradeNo) throws WxPayException {
        return wxService.closeOrder(outTradeNo);
    }

    @ApiOperation(value = "关闭订单")
    @PostMapping("/closeOrder")
    public WxPayOrderCloseResult closeOrder(@RequestBody WxPayOrderCloseRequest wxPayOrderCloseRequest) throws WxPayException {
        return wxService.closeOrder(wxPayOrderCloseRequest);
    }


    @ApiOperation(value = "统一下单，并组装所需支付参数")
    @PostMapping("/createOrder")
    public <T> T createOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return wxService.createOrder(request);
    }


    @ApiOperation(value = "原生的统一下单接口")
    @PostMapping("/unifiedOrder")
    public WxPayUnifiedOrderResult unifiedOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return wxService.unifiedOrder(request);
    }


    @ApiOperation(value = "退款")
    @PostMapping("/refund")
    public WxPayRefundResult refund(@RequestBody WxPayRefundRequest request) throws WxPayException {
        return wxService.refund(request);
    }


    @ApiOperation(value = "退款查询")
    @GetMapping("/refundQuery")
    public WxPayRefundQueryResult refundQuery(
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) String outTradeNo,
            @RequestParam(required = false) String outRefundNo,
            @RequestParam(required = false) String refundId
    ) throws WxPayException {
        return wxService.refundQuery(transactionId, outTradeNo, outRefundNo, refundId);
    }

    @ApiOperation(value = "退款查询")
    @PostMapping("/refundQuery")
    public WxPayRefundQueryResult refundQuery(@RequestBody WxPayRefundQueryRequest wxPayRefundQueryRequest) throws WxPayException {
        return wxService.refundQuery(wxPayRefundQueryRequest);
    }

    @ApiOperation(value = "支付回调通知处理")
    @PostMapping("/notify/order")
    public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult = wxService.parseOrderNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    @ApiOperation(value = "退款回调通知处理")
    @PostMapping("/notify/refund")
    public String parseRefundNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayRefundNotifyResult result = wxService.parseRefundNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    @ApiOperation(value = "扫码支付回调通知处理")
    @PostMapping("/notify/scanpay")
    public String parseScanPayNotifyResult(String xmlData) throws WxPayException {
        final WxScanPayNotifyResult result = wxService.parseScanPayNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }


    @ApiOperation(value = "发送红包")
    @PostMapping("/sendRedpack")
    public WxPaySendRedpackResult sendRedpack(@RequestBody WxPaySendRedpackRequest request) throws WxPayException {
        return wxService.getRedpackService().sendRedpack(request);
    }


    @ApiOperation(value = "查询红包")
    @GetMapping("/queryRedpack/{mchBillNo}")
    public WxPayRedpackQueryResult queryRedpack(@PathVariable String mchBillNo) throws WxPayException {
        return wxService.getRedpackService().queryRedpack(mchBillNo);
    }

    /**
     * <pre>
     * 扫码支付模式一生成二维码的方法
     * 二维码中的内容为链接，形式为：
     * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     * 其中XXXXX为商户需要填写的内容，商户将该链接生成二维码，如需要打印发布二维码，需要采用此格式。商户可调用第三方库生成二维码图片。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
     * </pre>
     *
     * @param productId  产品Id
     * @param logoFile   商户logo图片的文件对象，可以为空
     * @param sideLength 要生成的二维码的边长，如果为空，则取默认值400
     * @return 生成的二维码的字节数组
     */
    public byte[] createScanPayQrcodeMode1(String productId, File logoFile, Integer sideLength) throws Exception {
        return wxService.createScanPayQrcodeMode1(productId, logoFile, sideLength);
    }

    /**
     * <pre>
     * 扫码支付模式一生成二维码的方法
     * 二维码中的内容为链接，形式为：
     * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     * 其中XXXXX为商户需要填写的内容，商户将该链接生成二维码，如需要打印发布二维码，需要采用此格式。商户可调用第三方库生成二维码图片。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
     * </pre>
     *
     * @param productId 产品Id
     * @return 生成的二维码URL连接
     */
    public String createScanPayQrcodeMode1(String productId) {
        return wxService.createScanPayQrcodeMode1(productId);
    }

    /**
     * <pre>
     * 扫码支付模式二生成二维码的方法
     * 对应链接格式：weixin：//wxpay/bizpayurl?sr=XXXXX。请商户调用第三方库将code_url生成二维码图片。
     * 该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5
     * </pre>
     *
     * @param codeUrl    微信返回的交易会话的二维码链接
     * @param logoFile   商户logo图片的文件对象，可以为空
     * @param sideLength 要生成的二维码的边长，如果为空，则取默认值400
     * @return 生成的二维码的字节数组
     */
    public byte[] createScanPayQrcodeMode2(String codeUrl, File logoFile, Integer sideLength) throws Exception {
        return wxService.createScanPayQrcodeMode2(codeUrl, logoFile, sideLength);
    }


    @ApiOperation(value = "提交交易保障数据")
    @PostMapping("/report")
    public void report(@RequestBody WxPayReportRequest request) throws WxPayException {
        wxService.report(request);
    }


    @ApiOperation(value = "下载对账单")
    @GetMapping("/downloadBill/{billDate}/{billType}/{tarType}/{deviceInfo}")
    public WxPayBillResult downloadBill(
            @PathVariable String billDate, @PathVariable String billType,
            @PathVariable String tarType, @PathVariable String deviceInfo
    ) throws WxPayException {
        return wxService.downloadBill(billDate, billType, tarType, deviceInfo);
    }

    @ApiOperation(value = "下载对账单")
    @PostMapping("/downloadBill")
    public WxPayBillResult downloadBill(WxPayDownloadBillRequest wxPayDownloadBillRequest) throws WxPayException {
        return wxService.downloadBill(wxPayDownloadBillRequest);
    }


    @ApiOperation(value = "提交刷卡支付")
    @PostMapping("/micropay")
    public WxPayMicropayResult micropay(@RequestBody WxPayMicropayRequest request) throws WxPayException {
        return wxService.micropay(request);
    }


    @ApiOperation(value = "撤销订单")
    @PostMapping("/reverseOrder")
    public WxPayOrderReverseResult reverseOrder(@RequestBody WxPayOrderReverseRequest request) throws WxPayException {
        return wxService.reverseOrder(request);
    }

    @ApiOperation(value = "获取沙箱环境签名key")
    @GetMapping("/getSandboxSignKey")
    public String getSandboxSignKey() throws WxPayException {
        return wxService.getSandboxSignKey();
    }

    @ApiOperation(value = "发放代金券")
    @PostMapping("/sendCoupon")
    public WxPayCouponSendResult sendCoupon(@RequestBody WxPayCouponSendRequest request) throws WxPayException {
        return wxService.sendCoupon(request);
    }

    @ApiOperation(value = "查询代金券批次")
    @PostMapping("/queryCouponStock")
    public WxPayCouponStockQueryResult queryCouponStock(@RequestBody WxPayCouponStockQueryRequest request) throws WxPayException {
        return wxService.queryCouponStock(request);
    }

    @ApiOperation(value = "查询代金券信息")
    @PostMapping("/queryCouponInfo")
    public WxPayCouponInfoQueryResult queryCouponInfo(@RequestBody WxPayCouponInfoQueryRequest request) throws WxPayException {
        return wxService.queryCouponInfo(request);
    }

    @ApiOperation(value = "拉取订单评价数据")
    @PostMapping("/queryComment")
    public String queryComment(Date beginDate, Date endDate, Integer offset, Integer limit) throws WxPayException {
        return wxService.queryComment(beginDate, endDate, offset, limit);
    }

}

