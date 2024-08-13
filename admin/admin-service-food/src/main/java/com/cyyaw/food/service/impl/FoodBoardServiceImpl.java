package com.cyyaw.food.service.impl;

import com.cyyaw.food.service.FoodBoardService;
import com.cyyaw.food.table.dao.FoodBoardDao;
import com.cyyaw.food.table.entity.FoodBoard;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class FoodBoardServiceImpl extends BaseService<FoodBoard, Integer> implements FoodBoardService {

    @Autowired
    private FoodBoardDao foodBoardDao;

    @Override
    public BaseDao getBaseDao() {
        return foodBoardDao;
    }

    @Override
    public FoodBoard findByTid(String tid) {
        return foodBoardDao.findByTid(tid);
    }
}

