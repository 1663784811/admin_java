package com.cyyaw.admin.controller.pay;

import com.cyyaw.pay.service.PaySettingService;
import com.cyyaw.pay.table.entity.PaySetting;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "支付管理")
@RequestMapping("/admin/{eCode}/pay")
@RestController
public class AdminPayController {

    @Autowired
    private PaySettingService paySettingService;


    @ApiOperation(value = "支付设置", notes = "支付设置")
    @GetMapping("/saveSettings")
    public BaseResult<TAdmin> saveSettings(
            @RequestBody PaySetting paySetting,
            @PathVariable String eCode,
            @TokenData LoginInfo loginInfo
    ) {
        PaySetting save = paySettingService.save(paySetting, eCode);
        return BaseResult.ok(save);
    }


}
