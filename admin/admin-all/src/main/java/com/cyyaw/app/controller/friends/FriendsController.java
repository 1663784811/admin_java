package com.cyyaw.app.controller.friends;


import cn.hutool.json.JSONObject;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.entity.UUser;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "APP-好友")
@RequestMapping("/app/{appId}/friends")
@RestController
public class FriendsController {


    @Autowired
    private UUserService userService;


    @ApiOperation(value = "获取我的好友列表", notes = "获取我的好友列表")
    @GetMapping(value = "/myFriends")
    public BaseResult myFriends(@TokenData LoginInfo loginInfo, @PathVariable String appId) {
        String uid = loginInfo.getId();
        return BaseResult.ok(userService.myFriends(uid, appId));
    }

    @ApiOperation(value = "查询好友", notes = "查询好友")
    @GetMapping(value = "/findFriends")
    public BaseResult findFriends(String userId, @PathVariable String appId) {
        return BaseResult.ok(userService.findByTid(userId));
    }

    @ApiOperation(value = "添加好友", notes = "添加好友")
    @PostMapping(value = "/addFriends")
    public BaseResult addFriends(@TokenData LoginInfo loginInfo, String userId, @PathVariable String appId) {

        return BaseResult.ok();
    }


    @ApiOperation(value = "删除好友", notes = "删除好友")
    @PostMapping(value = "/delFriends")
    public BaseResult delFriends(@RequestBody UUser user, @TokenData LoginInfo loginInfo) {
        String userId = loginInfo.getId();
        String targetId = user.getTid();
        userService.delFriends(userId, targetId);
        return BaseResult.ok();
    }


    @ApiOperation(value = "查询好友", notes = "查询好友")
    @GetMapping(value = "/searchFriends")
    public BaseResult searchFriends(String userId, @PathVariable String appId) {
        List<UUser> userList = userService.findAll(new JSONObject());
        return BaseResult.ok(userList);
    }


}
