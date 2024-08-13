package com.cyyaw.app.controller.shopping;


import com.cyyaw.service.CartService;
import com.cyyaw.store.table.goods.entity.GCart;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.entity.AddMyCar;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "购物车")
@RestController
@RequestMapping("/app/{appId}/shopping/cart")
public class ShoppingCarController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "购物车", notes = "购物车")
    @GetMapping("/query")
    public BaseResult query(@TokenData LoginInfo loginInfo) {
        String userId = loginInfo.getId();
        return cartService.myCartList(userId);
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @PostMapping("/delCartGoods")
    public BaseResult delCartGoods(@RequestBody GCart gcart , @TokenData LoginInfo loginInfo) {
        String cartTid = gcart.getTid();
        String userId = loginInfo.getId();
        cartService.delCartGoods(cartTid, userId);

        return BaseResult.ok();
    }

    @ApiOperation(value = "更新购物车", notes = "更新购物车")
    @PostMapping("/updateCart")
    public BaseResult updateCar(@RequestBody AddMyCar addMyCar, @TokenData LoginInfo loginInfo) {
        String userId = loginInfo.getId();
        return cartService.updateMyCar(userId, addMyCar);
    }


}
