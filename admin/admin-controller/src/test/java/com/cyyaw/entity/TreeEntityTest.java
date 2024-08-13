//package com.cyyaw.entity;
//
//
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//
//public class TreeEntityTest {
//
//    public static void main(String[] args) {
//
//
//        TreeEntity treeEntity = new TreeEntity();
//        for (int i = 0; i < 10; i++) {
//            TreeEntity.Node node = new TreeEntity.Node();
//            node.setTid("" + i);
//            node.setName("mm-"+i);
//            treeEntity.add(node);
//            for (int j = 0; j < 3; j++) {
//                TreeEntity.Node node2 = new TreeEntity.Node();
//                node2.setTid( i + "-" + j);
//                node2.setPid(""+i);
//                node2.setName("ss-"+j);
//                treeEntity.add(node2);
//            }
//        }
//
//        JSONObject entries = new JSONObject(treeEntity);
//        String s = JSONUtil.toJsonStr(entries);
//
//
//        System.out.println(s);
//
//    }
//
//}
