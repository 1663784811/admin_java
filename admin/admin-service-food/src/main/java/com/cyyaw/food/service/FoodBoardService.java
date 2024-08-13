package com.cyyaw.food.service;

import com.cyyaw.food.table.entity.FoodBoard;
import com.cyyaw.jpa.BaseTableService;

public interface FoodBoardService extends BaseTableService<FoodBoard, Integer> {


    FoodBoard findByTid(String boardId);


}
