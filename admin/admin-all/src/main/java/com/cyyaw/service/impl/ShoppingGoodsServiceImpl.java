package com.cyyaw.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyyaw.enterprise.table.dao.EStoreDao;
import com.cyyaw.enterprise.table.entity.EStore;
import com.cyyaw.service.ShoppingGoodsService;
import com.cyyaw.store.table.goods.dao.*;
import com.cyyaw.store.table.goods.entity.*;
import com.cyyaw.util.entity.GoodsEntity;
import com.cyyaw.util.tools.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingGoodsServiceImpl implements ShoppingGoodsService {

    @Autowired
    private GGoodsSearchDao gGoodsSearchDao;

    @Autowired
    private EStoreDao eStoreDao;

    @Autowired
    private GGoodsDao gGoodsDao;

    @Autowired
    private GStoreGoodsSkuDao gStoreGoodsSkuDao;

    @Autowired
    private GPhotoDao gPhotoDao;

    @Autowired
    private GDetailsDao gDetailsDao;

    @Override
    public BaseResult<List<GoodsEntity>> searchGoods(GGoodsSearch goodsSearch) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest page = PageRequest.of(0, 10, sort);

        ExampleMatcher matcher = ExampleMatcher.matching();

        Example<GGoodsSearch> example = Example.of(goodsSearch, matcher);

        Page<GGoodsSearch> goodsSearchPage = gGoodsSearchDao.findAll(example, page);

        List<GGoodsSearch> goods = goodsSearchPage.getContent();
        BaseResult.Result result = new BaseResult.Result();
        result.setPage(goodsSearchPage.getTotalPages());
        result.setSize(goodsSearchPage.getSize());
        result.setTotal(goodsSearchPage.getTotalElements());

        //  ==========================================================
        List<String> goodsId = new ArrayList<>();
        List<String> storeId = new ArrayList<>();
        for (GGoodsSearch gds : goods) {
            goodsId.add(gds.getGoodsId());
            storeId.add(gds.getStoreGoodsId());
        }
        //  ==========================================================
        //  查商品
        List<GGoods> goodsList = gGoodsDao.findByTidIn(goodsId);
        //  查门店
        List<EStore> storeList = eStoreDao.findByTidIn(storeId);
        //  查sku
        List<GStoreGoodsSku> goodsSkuList = gStoreGoodsSkuDao.findAllByGoodsId(goodsId);

        // ======================  整理数据
        List<GoodsEntity> objList = new ArrayList<>();
        for (int i = 0; i < goods.size(); i++) {
            GGoodsSearch gGoodsSearch = goods.get(i);
            String gId = gGoodsSearch.getGoodsId();
            String sId = gGoodsSearch.getStoreGoodsId();

            GoodsEntity obj = new GoodsEntity();
            obj.setGoodsSearch(gGoodsSearch);
            // =============
            GGoods gGoods = new GGoods();
            for (int j = 0; j < goodsList.size(); j++) {
                GGoods gs = goodsList.get(j);
                if (gs.getTid().equals(gId)) {
                    gGoods = gs;
                    break;
                }
            }
            obj.setGGoods(gGoods);
            // =============
            EStore eStore = new EStore();
            for (int j = 0; j < storeList.size(); j++) {
                EStore store = storeList.get(j);
                if (store.getTid().equals(sId)) {
                    eStore = store;
                    break;
                }
            }
            obj.setEStore(eStore);
            // =============
            GStoreGoodsSku sku = new GStoreGoodsSku();
            List<GStoreGoodsSku> skuList = new ArrayList<>();
            for (int j = 0; j < goodsSkuList.size(); j++) {
                GStoreGoodsSku goodsSku = goodsSkuList.get(j);
                if (goodsSku.getGoodsId().equals(gId)) {
                    sku = goodsSku;
                    skuList.add(goodsSku);
                }
            }
            obj.setGStoreGoodsSku(sku);
            obj.setGStoreGoodsSkuList(skuList);
            objList.add(obj);
        }


        return BaseResult.ok(objList, result);
    }

    @Override
    public BaseResult goodsDetails(String skuId) {
        GStoreGoodsSku goodsSku = gStoreGoodsSkuDao.findByTid(skuId);
        String goodsId = goodsSku.getGoodsId();
        String storeId = goodsSku.getStoreId();
        // 查sku
        List<GStoreGoodsSku> goodsSkuList = gStoreGoodsSkuDao.findAllByGoodsId(goodsId);
        // 查商品
        GGoods goods = gGoodsDao.findByTid(goodsId);
        // 查门店
        EStore store = eStoreDao.findByTid(storeId);
        // =================================================  处理数据
        //处理sku
        Map<String, Set> skuAttr = getSkuAttr(goodsSkuList);
        // =================================================
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGStoreGoodsSku(goodsSku);
        goodsEntity.setGStoreGoodsSkuList(goodsSkuList);
        goodsEntity.setGGoods(goods);
        goodsEntity.setEStore(store);
        goodsEntity.setSkuAttr(skuAttr);
        return BaseResult.ok(goodsEntity);
    }

    private Map<String, Set> getSkuAttr(List<GStoreGoodsSku> goodsSkuList) {
        Map<String, Set> map = new HashMap<>();
        for (int i = 0; i < goodsSkuList.size(); i++) {
            GStoreGoodsSku gStoreGoodsSku = goodsSkuList.get(i);
            String attr = gStoreGoodsSku.getAttr();
            // 获取key
            JSONObject json = JSONUtil.parseObj(attr);
            for (String key : json.keySet()) {
                Object o = json.get(key);
                Set entity = map.get(key);
                if (null != entity) {
                    entity.add(o);
                } else {
                    entity = new HashSet<>();
                    entity.add(o);
                    map.put(key, entity);
                }
            }
        }
        return map;
    }

    @Override
    public BaseResult goodsPhoto(String goodsId) {
        if (StrUtil.isNotBlank(goodsId)) {
            GPhoto gPhoto = new GPhoto();
            gPhoto.setGoodsId(goodsId);
            Example<GPhoto> gPhotoExample = Example.of(gPhoto);
            List<GPhoto> gPhotoList = gPhotoDao.findAll(gPhotoExample);
            return BaseResult.ok(gPhotoList);
        }
        return BaseResult.fail();
    }

    @Override
    public BaseResult goodsDetailsText(String goodsId) {
        List<GDetails> gDetailsList = gDetailsDao.findByGoodsId(goodsId);
        if (gDetailsList.size() > 0) {
            return BaseResult.ok(gDetailsList.get(0));
        }
        return BaseResult.ok();
    }

    @Override
    public BaseResult findGoodsSku(String goodsId) {
        List<GStoreGoodsSku> goodsSkuList = gStoreGoodsSkuDao.findAllByGoodsId(goodsId);
        Map<String, Set> skuAttr = getSkuAttr(goodsSkuList);
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setSkuAttr(skuAttr);
        goodsEntity.setGStoreGoodsSkuList(goodsSkuList);
        return BaseResult.ok(goodsEntity);
    }

    @Override
    public BaseResult goodsTypeList(GGoods gGoods) {



        return null;
    }

}
