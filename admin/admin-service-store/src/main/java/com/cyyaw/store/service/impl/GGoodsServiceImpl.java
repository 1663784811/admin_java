package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.GGoodsService;
import com.cyyaw.store.table.goods.dao.GGoodsDao;
import com.cyyaw.store.table.goods.dao.GGoodsSearchDao;
import com.cyyaw.store.table.goods.entity.GGoods;
import com.cyyaw.store.table.goods.entity.GGoodsSearch;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;


@Slf4j
@Service
public class GGoodsServiceImpl extends BaseService<GGoods, Integer> implements GGoodsService {

    @Autowired
    private GGoodsDao gGoodsDao;

    @Autowired
    private GGoodsSearchDao gGoodsSearchDao;


    @Override
    public BaseDao getBaseDao() {
        return gGoodsDao;
    }

    @Override
    public GGoods findByTid(String tid) {
        return gGoodsDao.findByTid(tid);
    }

    @Override
    public BaseResult saveGGoods(GGoods saveObj) {
        GGoods obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = gGoodsDao.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            GGoods cpObj = gGoodsDao.findByid(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj, cpObj);
            obj = gGoodsDao.save(cpObj);
        }

        String tid = obj.getTid();
        // 同步搜索表
        GGoodsSearch gGoodsSearch = gGoodsSearchDao.findByGoodsId(tid);
        if (null != gGoodsSearch) {
            gGoodsSearch.setName(obj.getName());

            gGoodsSearchDao.save(gGoodsSearch);
        } else {
            gGoodsSearch = new GGoodsSearch();
            gGoodsSearch.setName(obj.getName());
            gGoodsSearch.setGoodsId(tid);
            gGoodsSearch.setEvaluate(5);
            gGoodsSearch.setIsTop(0);
            gGoodsSearch.setTid(WhyStringUtil.getUUID());
            gGoodsSearch.setCreateTime(new Date());
            gGoodsSearch.setDel(0);
            gGoodsSearch.setLowPrice(new BigDecimal("0"));
            gGoodsSearch.setHighPrice(new BigDecimal("0"));
            gGoodsSearch.setStoreGoodsId(obj.getStoreId());
            gGoodsSearchDao.save(gGoodsSearch);
        }
        return BaseResult.ok(obj);
    }
}

