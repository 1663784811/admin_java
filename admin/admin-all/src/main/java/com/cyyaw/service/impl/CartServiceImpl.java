package com.cyyaw.service.impl;

import com.cyyaw.enterprise.table.dao.EStoreDao;
import com.cyyaw.enterprise.table.entity.EStore;
import com.cyyaw.service.CartService;
import com.cyyaw.store.service.GCartService;
import com.cyyaw.store.table.goods.dao.GCartDao;
import com.cyyaw.store.table.goods.dao.GGoodsDao;
import com.cyyaw.store.table.goods.dao.GStoreGoodsSkuDao;
import com.cyyaw.store.table.goods.entity.GCart;
import com.cyyaw.store.table.goods.entity.GGoods;
import com.cyyaw.store.table.goods.entity.GStoreGoodsSku;
import com.cyyaw.util.entity.AddMyCar;
import com.cyyaw.util.entity.CartListResponse;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyException;
import com.cyyaw.util.tools.WhyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private GCartDao gCartDao;

    @Autowired
    private GCartService gCartService;

    @Autowired
    private EStoreDao eStoreDao;

    @Autowired
    private GStoreGoodsSkuDao gStoreGoodsSkuDao;

    @Autowired
    private GGoodsDao gGoodsDao;

    @Override
    public BaseResult myCartList(String userId) {
        //  第一步: 根据门店分组，查出有多少门店
        GCart gCart = new GCart();
        gCart.setUserId(userId);
        ExampleMatcher matcher = ExampleMatcher.matching();
        PageRequest of = PageRequest.of(0, 10);
        Example<GCart> ex = Example.of(gCart, matcher);
        Page<GCart> gCartPage = gCartDao.findAll(ex, of);
        BaseResult.Result result = new BaseResult.Result();
        result.setPage(gCartPage.getTotalPages());
        result.setSize(gCartPage.getSize());
        result.setTotal(gCartPage.getTotalElements());
        List<GCart> gCartList = gCartPage.getContent();
        List<String> storeIdList = new ArrayList<>();
        for (GCart cart : gCartList) {
            storeIdList.add(cart.getStoreId());
        }
        List<CartListResponse> data = cartList(storeIdList, userId);
        return BaseResult.ok(data, result);
    }

    @Override
    public BaseResult myStoreCartList(String storeId, String userId) {
        List<String> storeIdList = new ArrayList<>();
        storeIdList.add(storeId);
        List<CartListResponse> cartList = cartList(storeIdList, userId);
        if (!cartList.isEmpty()) {
            return BaseResult.ok(cartList.get(0).getCartList());
        }
        return BaseResult.ok(cartList);
    }


    @Override
    public BaseResult updateMyCar(String userId, AddMyCar addMyCar) {
        Integer number = addMyCar.getNumber();
        if (number == null || number.equals(0)) {
            throw new WhyException("参数错误");
        }
        // 第一步: 查询用户是否已经把该商品加入购物车了
        String skuId = addMyCar.getSkuId();
        GCart gCart = gCartService.findBySkuIdAndUid(skuId, userId);
        if (null == gCart) {
            // 添加购物车
            GStoreGoodsSku goodsSku = gStoreGoodsSkuDao.findByTid(skuId);
            gCart = new GCart();
            gCart.setTid(WhyStringUtil.getUUID());
            gCart.setCreateTime(new Date());
            gCart.setDel(0);
            gCart.setNote("");
            gCart.setUserId(userId);
            gCart.setStoreId(goodsSku.getStoreId());
            gCart.setGoodsId(goodsSku.getGoodsId());
            gCart.setSkuId(goodsSku.getTid());
            gCart.setNumber(number);
            gCart = gCartDao.save(gCart);
        } else {
            // 修改sku数量
            Integer nb = gCart.getNumber();
            gCart.setNumber(nb + number);
            if (gCart.getNumber() == 0) {
                throw new WhyException("数量有误");
            }
            gCart = gCartDao.save(gCart);
        }
        return BaseResult.ok(gCart);
    }

    @Override
    public void delCartGoods(String cartTid, String userId) {
        List<GCart> cartList = gCartDao.findByTidAndUserId(cartTid, userId);
        for (int i = 0; i < cartList.size(); i++) {
            gCartDao.delete(cartList.get(i));
        }
    }


    /**
     * 查询我的购物车里的
     */
    private List<CartListResponse> cartList(List<String> storeIdList, String userId) {
        // 第二步:获取门店
        List<EStore> storeList = eStoreDao.findByTidIn(storeIdList);
        // 第三步: 查询购物车里的商品(sku)
        List<GCart> gCartListObj = gCartDao.findByUidAndStoreIdIn(userId, storeIdList);
        // 第四步: 查商品SKU
        List<String> skuIdList = new ArrayList<>();
        List<String> goodsIdList = new ArrayList<>();
        for (int i = 0; i < gCartListObj.size(); i++) {
            skuIdList.add(gCartListObj.get(i).getSkuId());
            goodsIdList.add(gCartListObj.get(i).getGoodsId());
        }
        List<GStoreGoodsSku> goodsSkuList = gStoreGoodsSkuDao.findByTidIn(skuIdList);
        // 第五步: 查询商品
        List<GGoods> goodsList = gGoodsDao.findByTidIn(goodsIdList);
        // ================ 整理数据
        List<CartListResponse> data = new ArrayList<>();
        for (int i = 0; i < storeList.size(); i++) {
            EStore storeObj = storeList.get(i);
            String storeId = storeObj.getTid();
            CartListResponse rest = new CartListResponse();
            rest.setEStore(storeObj);
            // ========== 整理购物车
            List<GCart> cartList = new ArrayList();
            for (int j = 0; j < gCartListObj.size(); j++) {
                GCart cart = gCartListObj.get(j);
                String stId = cart.getStoreId();
                if (storeId.equals(stId)) {
                    // ====== 整理sku
                    for (int k = 0; k < goodsSkuList.size(); k++) {
                        GStoreGoodsSku gStoreGoodsSku = goodsSkuList.get(k);
                        if (gStoreGoodsSku.getTid().equals(cart.getSkuId())) {
                            cart.setGoodsSku(gStoreGoodsSku);
                            break;
                        }
                    }
                    String goodsId = cart.getGoodsId();
                    for (int k = 0; k < goodsList.size(); k++) {
                        GGoods gGoods = goodsList.get(k);
                        if (gGoods.getTid().equals(goodsId)) {
                            cart.setGoods(gGoods);
                            break;
                        }
                    }
                    cartList.add(cart);
                }
            }
            rest.setCartList(cartList);
            data.add(rest);
        }
        return data;
    }


}
