package com.cyyaw.app.controller;


import com.cyyaw.blackroom.service.BlackRoomService;
import com.cyyaw.blackroom.table.entity.BlackRoom;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "app-小黑屋")
@RestController
@RequestMapping("/app/{appId}/black/room")
public class BlackRoomController {

    @Autowired
    private BlackRoomService blackRoomService;

    @ApiOperation(value = "保存小黑人", notes = "保存小黑人")
    @PostMapping("/saveBlackPeople")
    public BaseResult saveBlackPeople(@RequestBody BlackRoom blackRoom){
        BlackRoom save = blackRoomService.save(blackRoom);
        return BaseResult.ok(save);
    }

}
