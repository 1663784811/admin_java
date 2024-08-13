package com.cyyaw.app.controller.user;


import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.UAddressService;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.entity.UAddress;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "app-用户地址")
@RequestMapping("/app/{appId}/address")
@RestController
public class AppAddressController {


    @Autowired
    private UUserService uUserService;

    @Autowired
    private UAddressService uAddressService;


    @GetMapping("/defaultAddress")
    @ApiOperation(value = "app用户默认地址", notes = "app用户默认地址")
    public BaseResult defaultAddress(String addressId, @TokenData LoginInfo loginInfo) {
        String userId = loginInfo.getId();
        return BaseResult.ok(uAddressService.defaultAddress(userId, addressId));
    }

    @PostMapping("/saveAddress")
    @ApiOperation(value = "app用户保存地址", notes = "app用户保存地址")
    public BaseResult saveAddress(UAddress uAddress, @TokenData LoginInfo loginInfo) {
        uAddress.setUserId(loginInfo.getId());
        return BaseResult.ok(uAddressService.saveAddress(uAddress));
    }

}
