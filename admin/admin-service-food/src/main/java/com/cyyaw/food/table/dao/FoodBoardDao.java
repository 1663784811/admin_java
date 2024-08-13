package com.cyyaw.food.table.dao;


import com.cyyaw.food.table.entity.FoodBoard;
import com.cyyaw.jpa.BaseDao;
import org.springframework.data.jpa.repository.Query;

public interface FoodBoardDao extends BaseDao<FoodBoard, Integer> {


    @Query("select m from FoodBoard m where m.tid =?1")
    FoodBoard findByTid(String tid);


}
