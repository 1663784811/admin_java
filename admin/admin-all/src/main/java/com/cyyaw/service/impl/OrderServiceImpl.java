package com.cyyaw.service.impl;

import com.cyyaw.config.exception.WebException;
import com.cyyaw.enterprise.table.dao.EStoreDao;
import com.cyyaw.enterprise.table.entity.EStore;
import com.cyyaw.service.OrderService;
import com.cyyaw.store.table.goods.dao.GGoodsDao;
import com.cyyaw.store.table.goods.dao.GStoreGoodsSkuDao;
import com.cyyaw.store.table.goods.entity.GGoods;
import com.cyyaw.store.table.goods.entity.GStoreGoodsSku;
import com.cyyaw.store.table.order.dao.ODetailsDao;
import com.cyyaw.store.table.order.dao.OOrderDao;
import com.cyyaw.store.table.order.entity.ODetails;
import com.cyyaw.store.table.order.entity.OOrder;
import com.cyyaw.util.entity.UserOrderResponse;
import com.cyyaw.util.entity.*;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GStoreGoodsSkuDao gStoreGoodsSkuDao;

    @Autowired
    private EStoreDao eStoreDao;

    @Autowired
    private GGoodsDao gGoodsDao;

    @Autowired
    private OOrderDao oOrderDao;

    @Autowired
    private ODetailsDao oDetailsDao;

    @Override
    public CountGoodsRst countGoodsPrice(SubmitOrder submitOrder) {
        List<StoreRest> storeRestList = new ArrayList<>();

        List<CountGoods> goodsList = submitOrder.getGoodsList();
        List<String> skuIdList = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {
            CountGoods countGoods = goodsList.get(i);
            skuIdList.add(countGoods.getSkuId());
        }

        // 查询sku
        List<GStoreGoodsSku> goodsSkuList = gStoreGoodsSkuDao.findByTidIn(skuIdList);

        List<String> storeIdList = new ArrayList<>();
        List<String> goodsIdList = new ArrayList<>();
        for (GStoreGoodsSku sku : goodsSkuList) {
            String storeId = sku.getStoreId();
            String gId = sku.getGoodsId();
            storeIdList.add(storeId);
            goodsIdList.add(gId);
        }
        // 门店
        List<EStore> storeList = eStoreDao.findByTidIn(storeIdList);
        // 查商品
        List<GGoods> gGoodsList = gGoodsDao.findByTidIn(goodsIdList);

        // 拆分门店
        for (int i = 0; i < goodsList.size(); i++) {
            CountGoods countGoods = goodsList.get(i);
            String skuId = countGoods.getSkuId();
            Integer number = countGoods.getNumber();
            // 查找
            for (int j = 0; j < goodsSkuList.size(); j++) {
                GStoreGoodsSku gStoreGoodsSku = goodsSkuList.get(j);
                String storeId = gStoreGoodsSku.getStoreId();
                String skuTid = gStoreGoodsSku.getTid();
                String goodsId = gStoreGoodsSku.getGoodsId();
                if (skuTid.equals(skuId)) {
                    EStore restStore = null;
                    for (int k = 0; k < storeList.size(); k++) {
                        EStore store = storeList.get(k);
                        String storeTid = store.getTid();
                        if (storeTid.equals(storeId)) {
                            restStore = store;
                            break;
                        }
                    }
                    //==
                    if (restStore != null) {
                        String storeTid = restStore.getTid();
                        boolean h = false;
                        for (int k = 0; k < storeRestList.size(); k++) {
                            StoreRest storeRest = storeRestList.get(k);
                            String sId = storeRest.getStoreId();
                            if (sId.equals(storeTid)) {
                                // 找到了
                                List<GoodsRest> goodsRestList = storeRest.getGoodsRestList();
                                // set商品
                                GoodsRest goodsRest = new GoodsRest();
                                for (int m = 0; m < gGoodsList.size(); m++) {
                                    GGoods gGoods = gGoodsList.get(m);
                                    String tid = gGoods.getTid();
                                    if (tid.equals(goodsId)) {
                                        goodsRest.setGGoods(gGoods);
                                        break;
                                    }
                                }
                                goodsRest.setGStoreGoodsSku(gStoreGoodsSku);
                                goodsRest.setNumber(number);
                                BigDecimal price = gStoreGoodsSku.getPrice();
                                goodsRest.setPrice(price);
                                goodsRest.setTotalPrice(price.multiply(new BigDecimal(number)));
                                goodsRestList.add(goodsRest);
                                // ===  更新价格
                                BigDecimal allPrice = storeRest.getAllTotalPrice();
                                storeRest.setAllTotalPrice(allPrice.add(goodsRest.getTotalPrice()));
                                BigDecimal goodsPrice = storeRest.getGoodsTotalPrice();
                                storeRest.setGoodsTotalPrice(goodsPrice.add(goodsRest.getTotalPrice()));
                                BigDecimal goodsN = storeRest.getGoodsNum();
                                storeRest.setGoodsNum(goodsN.add(new BigDecimal(goodsRest.getNumber())));
                                BigDecimal exp = storeRest.getExpressPrice();
                                storeRest.setExpressPrice(exp.add(new BigDecimal("0")));
                                h = true;
                                break;
                            }
                        }

                        // 没有找到
                        if (!h) {
                            //
                            List<GoodsRest> goodsRestList = new ArrayList<>();
                            GoodsRest goodsRest = new GoodsRest();
                            for (int k = 0; k < gGoodsList.size(); k++) {
                                GGoods gGoods = gGoodsList.get(k);
                                String tid = gGoods.getTid();
                                if (tid.equals(goodsId)) {
                                    goodsRest.setGGoods(gGoods);
                                    break;
                                }
                            }
                            goodsRest.setGStoreGoodsSku(gStoreGoodsSku);
                            goodsRest.setNumber(number);
                            BigDecimal price = gStoreGoodsSku.getPrice();
                            goodsRest.setPrice(price);
                            goodsRest.setTotalPrice(price.multiply(new BigDecimal(number)));
                            goodsRestList.add(goodsRest);
                            // ===
                            StoreRest storeRest = new StoreRest();
                            storeRest.setGoodsRestList(goodsRestList);
                            storeRest.setAllTotalPrice(goodsRest.getTotalPrice());
                            storeRest.setGoodsTotalPrice(goodsRest.getTotalPrice());
                            storeRest.setGoodsNum(new BigDecimal(goodsRest.getNumber()));
                            storeRest.setExpressPrice(new BigDecimal("0"));
                            storeRest.setStoreId(storeTid);
                            storeRest.setEStore(restStore);
                            storeRestList.add(storeRest);
                        }
                    }
                }
            }

        }
        // =================================   整理数据
        BigDecimal allTotalPrice = new BigDecimal("0");
        BigDecimal goodsTotalPrice = new BigDecimal("0");
        BigDecimal goodsNum = new BigDecimal("0");
        BigDecimal expressPrice = new BigDecimal("0");
        for (int i = 0; i < storeRestList.size(); i++) {
            StoreRest storeRest = storeRestList.get(i);
            allTotalPrice = allTotalPrice.add(storeRest.getAllTotalPrice());
            goodsTotalPrice = goodsTotalPrice.add(storeRest.getGoodsTotalPrice());
            goodsNum = goodsNum.add(storeRest.getGoodsNum());
            expressPrice = expressPrice.add(storeRest.getExpressPrice());
        }
        CountGoodsRst countGoodsRst = new CountGoodsRst();
        countGoodsRst.setAllTotalPrice(allTotalPrice);
        countGoodsRst.setGoodsTotalPrice(goodsTotalPrice);
        countGoodsRst.setGoodsNum(goodsNum);
        countGoodsRst.setExpressPrice(expressPrice);
        countGoodsRst.setStoreRestList(storeRestList);
        return countGoodsRst;
    }

    @Override
    public OOrder createOrder(SubmitOrder submitOrder) {
        List<CountGoods> goodsList = submitOrder.getGoodsList();
        String userId = submitOrder.getUid();
        String userName = submitOrder.getUserName();
        String addressId = submitOrder.getAddressId();
        String address = submitOrder.getAddress();
        String phone = submitOrder.getPhone();
        String description = submitOrder.getDescription();

        // 第一步:  判断基本信息是否正确
        List<String> skuIdList = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {
            CountGoods countGoods = goodsList.get(i);
            skuIdList.add(countGoods.getSkuId());
        }
        // 查询sku
        List<GStoreGoodsSku> goodsSkuList = gStoreGoodsSkuDao.findByTidIn(skuIdList);
        for (int i = 0; i < goodsList.size(); i++) {
            CountGoods countGoods = goodsList.get(i);
            String skuId = countGoods.getSkuId();
            Integer number = countGoods.getNumber();
            boolean h = false;
            for (int j = 0; j < goodsSkuList.size(); j++) {
                GStoreGoodsSku gStoreGoodsSku = goodsSkuList.get(j);
                String skuTid = gStoreGoodsSku.getTid();
                if (skuId.equals(skuTid)) {
                    // 判断库存是否足够
                    Integer skuNumber = gStoreGoodsSku.getNumber();
                    if (number > skuNumber) {
                        GGoods goods = gGoodsDao.findByTid(gStoreGoodsSku.getGoodsId());
                        WebException.fail("购物的商品:【" + goods.getName() + "】库存不足");
                    }
                    h = true;
                    break;
                }
            }
            if (!h) {
                WebException.fail("购物的商品不存在");
            }
        }
        // 第二步:  计算价格
        CountGoodsRst countGoodsRst = countGoodsPrice(submitOrder);
        BigDecimal goodsNum = countGoodsRst.getGoodsNum();
        BigDecimal allTotalPrice = countGoodsRst.getAllTotalPrice();
        BigDecimal expressPrice = countGoodsRst.getExpressPrice();
        // 第三步: 生成订单
        List<StoreRest> storeRestList = countGoodsRst.getStoreRestList();
        // 判断是否要生成组合订单
        OOrder restOrder = null;
        if (storeRestList.size() > 1) {
            // 生成组合订单
            OOrder order = new OOrder();
            order.setTid(WhyStringUtil.getUUID());
            order.setCreateTime(new Date());
            order.setDel(0);
            order.setNote("组合订单");
            order.setUserId(userId);
            order.setUserName(userName);

            order.setEnterpriseCode("");
            order.setStoreId("");
            order.setStoreName("");

            order.setOrderNo(WhyStringUtil.createOrderNum());
            order.setType(1);
            order.setStatus(0);
            order.setAddressId(addressId);
            order.setAddressDetail(address);
            order.setPhone(phone);
            order.setDescription(description);
            order.setNumber(goodsNum.intValue());
            order.setAmount(allTotalPrice);
            order.setExpressPrice(expressPrice);
            order.setPayableAmount(allTotalPrice);
            order.setPayType(null);
            oOrderDao.save(order);
            for (int i = 0; i < storeRestList.size(); i++) {
                StoreRest storeRest = storeRestList.get(i);
                createOrder(storeRest, submitOrder);
            }
            restOrder = order;
        } else if (storeRestList.size() == 1) {
            // 生成正常订单
            StoreRest storeRest = storeRestList.get(0);
            OOrder order = createOrder(storeRest, submitOrder);
            restOrder = order;
        }
        return restOrder;
    }

    @Override
    public BaseResult findMyOrder(String userId) {
        // 查询我的订单
        PageRequest pageRequest = PageRequest.of(0, 20);
        OOrder od = new OOrder();
        od.setUserId(userId);
        Example<OOrder> example = Example.of(od);
        Page<OOrder> oOrderPage = oOrderDao.findAll(example, pageRequest);
        List<OOrder> orderList = oOrderPage.getContent();

        BaseResult.Result result = new BaseResult.Result();
        result.setPage(oOrderPage.getTotalPages());
        result.setSize(oOrderPage.getSize());
        result.setTotal(oOrderPage.getTotalElements());
        //=====
        List<String> orderIdList = new ArrayList<>();
        List<String> storeIdList = new ArrayList<>();
        for (OOrder order : orderList) {
            orderIdList.add(order.getTid());
            storeIdList.add(order.getStoreId());
        }
        //=====  查询订单详情
        List<ODetails> oDetailsList = oDetailsDao.findByOrderIdArr(orderIdList);
        //=====  查询门店
        List<EStore> storeList = eStoreDao.findByTidIn(storeIdList);
        //=====  整理数据
        List<UserOrderResponse> data = new ArrayList<>();
        for (OOrder order : orderList) {
            String tid = order.getTid();
            String storeId = order.getStoreId();
            UserOrderResponse userOrderResponse = new UserOrderResponse();
            userOrderResponse.setOrder(order);
            // 详情
            List<ODetails> oDetails = new ArrayList<>();
            for (int i = 0; i < oDetailsList.size(); i++) {
                ODetails details = oDetailsList.get(i);
                if (tid.equals(details.getOrderId())) {
                    oDetails.add(details);
                }
            }
            userOrderResponse.setDetailsList(oDetails);
            // 门店
            for (int i = 0; i < storeList.size(); i++) {
                EStore store = storeList.get(i);
                if (store.getTid().equals(storeId)) {
                    userOrderResponse.setStore(store);
                    break;
                }
            }
            data.add(userOrderResponse);
        }
        return BaseResult.ok(data, result);
    }

    @Override
    public BaseResult orderById(String orderId) {
        OOrder order = oOrderDao.findByTid(orderId);
        List<ODetails> oDetailsList = oDetailsDao.findByOrderId(orderId);
        EStore store = eStoreDao.findByTid(order.getStoreId());
        UserOrderResponse data = new UserOrderResponse();
        data.setOrder(order);
        data.setDetailsList(oDetailsList);
        data.setStore(store);
        return BaseResult.ok(data);
    }

    @Override
    public OOrder boardOrder(String boardId) {
        OOrder order = oOrderDao.findNowBoardOrder(boardId);
        if (null != order) {
            String tid = order.getTid();
            List<ODetails> oDetailsList = oDetailsDao.findByOrderId(tid);
            order.setODetailsList(oDetailsList);
            return order;
        }
        return null;
    }

    @Override
    public OOrder createFoodOrder(String boardId, SubmitOrder submitOrder) {
        String description = submitOrder.getDescription();
        // 第一点: 计算商品价格
        CountGoodsRst countGoodsRst = countGoodsPrice(submitOrder);
        List<StoreRest> storeRestList = countGoodsRst.getStoreRestList();
        if (storeRestList == null || storeRestList.size() > 1) {
            WebException.fail("非法提交");
        }
        StoreRest storeRest = storeRestList.get(0);
        List<GoodsRest> goodsRestList = storeRest.getGoodsRestList();
        EStore eStore = storeRest.getEStore();
        BigDecimal goodsNum = storeRest.getGoodsNum();
        //查询台桌是否有没有结束的订单
        OOrder order = boardOrder(boardId);
        List<ODetails> oDetailsList = new ArrayList<>();
        if (null == order) {
            order = new OOrder();
            order.setTid(WhyStringUtil.getUUID());
            order.setCreateTime(new Date());
            order.setDel(0);
            order.setNote("");
            order.setUserId("");
            order.setBoardId(boardId);
            order.setUserName("点餐订单");

            order.setEnterpriseCode(eStore.getEnterpriseCode());
            order.setStoreId(eStore.getTid());
            order.setStoreName(eStore.getName());

            order.setOrderNo(WhyStringUtil.createOrderNum());
            order.setType(1);
            order.setStatus(0);
            order.setDescription(description);
            order.setNumber(goodsNum.intValue());
            order.setAmount(BigDecimal.ZERO);
            order.setExpressPrice(BigDecimal.ZERO);
            order.setPayableAmount(BigDecimal.ZERO);
            order.setPayType(null);
        } else {
            // 查找订单详情
            oDetailsList.addAll(oDetailsDao.findByOrderId(order.getTid()));
        }
        // 钱
        order.setAmount(order.getAmount().add(storeRest.getGoodsTotalPrice()));
        order.setExpressPrice(order.getExpressPrice().add(storeRest.getExpressPrice()));
        order.setPayableAmount(order.getPayableAmount().add(storeRest.getAllTotalPrice()));
        order.setStatus(0);
        oOrderDao.save(order);
        // 保存订单详情
        oDetailsList.addAll(saveOrderDetails(goodsRestList, order.getTid()));
        order.setODetailsList(oDetailsList);
        return order;
    }

    private OOrder createOrder(StoreRest storeRest, SubmitOrder submitOrder) {
        String userId = submitOrder.getUid();
        String userName = submitOrder.getUserName();
        String addressId = submitOrder.getAddressId();
        String address = submitOrder.getAddress();
        String phone = submitOrder.getPhone();
        String description = submitOrder.getDescription();
        // ===
        BigDecimal allTotalPrice = storeRest.getAllTotalPrice();
        BigDecimal goodsNum = storeRest.getGoodsNum();
        BigDecimal expressPrice = storeRest.getExpressPrice();
        String storeId = storeRest.getStoreId();
        EStore eStore = storeRest.getEStore();
        // ====
        OOrder order = new OOrder();
        order.setTid(WhyStringUtil.getUUID());
        order.setCreateTime(new Date());
        order.setDel(0);
        order.setNote("");
        order.setUserId(userId);
        order.setUserName(userName);

        order.setEnterpriseCode(eStore.getEnterpriseCode());
        order.setStoreId(storeId);
        order.setStoreName(eStore.getName());

        order.setOrderNo(WhyStringUtil.createOrderNum());
        order.setType(1);
        order.setStatus(0);
        order.setAddressId(addressId);
        order.setAddressDetail(address);
        order.setPhone(phone);
        order.setDescription(description);
        order.setNumber(goodsNum.intValue());
        order.setAmount(allTotalPrice);
        order.setExpressPrice(expressPrice);
        order.setPayableAmount(allTotalPrice);
        order.setPayType(null);
        oOrderDao.save(order);
        // 生成订单详情
        List<GoodsRest> goodsRestList = storeRest.getGoodsRestList();
        List<ODetails> oDetailsList = saveOrderDetails(goodsRestList, order.getTid());
        order.setODetailsList(oDetailsList);
        return order;
    }

    /**
     * 保存订单详情
     */
    private List<ODetails> saveOrderDetails(List<GoodsRest> goodsRestList, String orderId) {
        List<ODetails> oDetailsList = new ArrayList<>();
        for (int i = 0; i < goodsRestList.size(); i++) {
            GoodsRest goodsRest = goodsRestList.get(i);
            ODetails oDetails = new ODetails();
            oDetails.setTid(WhyStringUtil.getUUID());
            oDetails.setCreateTime(new Date());
            oDetails.setDel(0);
            oDetails.setNote("");
            oDetails.setOrderId(orderId);
            oDetails.setGoodsId(goodsRest.getGGoods().getTid());
            oDetails.setSkuId(goodsRest.getGStoreGoodsSku().getTid());
            oDetails.setType(0);
            oDetails.setName(goodsRest.getGGoods().getName());
            oDetails.setPhoto(goodsRest.getGStoreGoodsSku().getPhoto());
            oDetails.setPrice(goodsRest.getGStoreGoodsSku().getPrice());
            oDetails.setLastPrice(goodsRest.getGStoreGoodsSku().getPrice());
            oDetails.setNumber(goodsRest.getNumber());
            oDetailsList.add(oDetailsDao.save(oDetails));
        }
        return oDetailsList;
    }


}
