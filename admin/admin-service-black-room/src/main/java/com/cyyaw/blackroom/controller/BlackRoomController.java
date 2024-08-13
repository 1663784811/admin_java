//package com.cyyaw.backroom.controller;
//
//import com.cyyaw.backroom.service.BlackRoomService;
//import com.cyyaw.backroom.table.entity.BlackRoom;
//import com.cyyaw.jpa.BaseConstants;
//import com.cyyaw.jpa.util.entity.SelectEntity;
//import com.cyyaw.util.tools.BaseResult;
//import com.cyyaw.util.tools.PageRespone;
//import com.cyyaw.util.tools.WhyStringUtil;
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.Assert;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.Map;
//
//
//@Slf4j
//@Api(tags = "black_room")
//@RequestMapping("/admin/black_room")
//@RestController
//public class BlackRoomController {
//
//    @Autowired
//    private BlackRoomService blackRoomService;
//
//    /**
//     * 表:black_room ===> 所有:带条件
//     */
//    @GetMapping(value = "/findAllBlackRoom")
//    public void findAllBlackRoom(HttpServletResponse response, String jsonStr, SelectEntity selectEntity) {
//        List<BlackRoom> list = blackRoomService.findAll(jsonStr, selectEntity);
//        ResponseUtils.responseJsonFilter(response, list,BlackRoomConst.filterselectColumnArr);
//    }
//
//    /**
//     * 分页条件查询
//     */
//    @GetMapping(value = "/findPage")
//    public BaseResult<BlackRoom> findPage(@RequestParam Map<String, Object> map) {
//        PageRespone<BlackRoom> page = blackRoomService.findPage(new JSONObject(map));
//        return BaseResult.ok(page);
//    }
//
//    /**
//     * 根据ID查询
//     */
//    @GetMapping(value = "/findIdBlackRoom")
//    public void findIdBlackRoom(HttpServletResponse response,@RequestParam Integer id) {
//        BlackRoom obj = blackRoomService.findId(id);
//        ResponseUtils.responseJsonFilter(response, obj,BlackRoomConst.filterselectColumnArr);
//    }
//
//
//    /**
//     * 添加或修改
//     */
//    @PostMapping(value = "/saveBlackRoom")
//    public void saveBlackRoom(HttpServletResponse response,@RequestBody BlackRoom blackRoom) {
//        BlackRoom obj = null;
//        //添加
//        Integer id = blackRoom.getId();
//        if (null == id) {
//            //添加
//            log.info("添加:{}", blackRoom);
//            WhyBeanUtils.filterField(blackRoom, BlackRoomConst.filteraddColumnArr);
//            blackRoom.setTid(WhyStringUtil.getUUID());
//            obj = blackRoomService.save(blackRoom);
//        } else {
//            //修改
//            log.info("修改:{}", blackRoom);
//            BlackRoom blackRoom1 = blackRoomService.findId(id);
//            Assert.notNull(blackRoom1, "操作失败！");
//            WhyBeanUtils.filterField(blackRoom, BlackRoomConst.filteraddColumnArr);
//            obj = blackRoomService.save(blackRoom);
//        }
//        ResponseUtils.responseJsonFilter(response, obj,BlackRoomConst.filterselectColumnArr);
//    }
//
//    /**
//     * 删除
//     */
//    @PostMapping(value = "/delBlackRoom")
//    public Map delBlackRoom( @RequestBody Integer idArr[]) {
//        blackRoomService.del(idArr);
//        return BaseConstants.tableDelSuccess;
//    }
//
//}
