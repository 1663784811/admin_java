package com.cyyaw.user.utils;

import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.entity.MenuEntity;
import com.cyyaw.user.utils.entity.TreeEntity;
import com.cyyaw.util.tools.BaseResult;

import java.util.ArrayList;
import java.util.List;

public class MenuUtils {


    public static List<MenuEntity> getMenu(TreeEntity<TPower> data) {
        List<TreeEntity.Node<TPower>> root = data.getRoot();
        List<MenuEntity> restData = new ArrayList<>();
        for (int i = 0; i < root.size(); i++) {
            TreeEntity.Node<TPower> tPowerNode = root.get(i);
            restData.add(getNodeData(tPowerNode));
        }
        return restData;
    }


    public static MenuEntity getNodeData(TreeEntity.Node<TPower> node) {
        TPower data = node.getData();
        MenuEntity menu = new MenuEntity();
        menu.setTitle(node.getTitle());
        menu.setIcon(data.getIcon());
        menu.setRouteName(data.getRouteName());
        menu.setUrl(data.getUrl());
//        menu.setParams();

        List<MenuEntity> children = new ArrayList<>();
        List<TreeEntity.Node> childrenList = node.getChildren();
        if (null != childrenList && childrenList.size() > 0) {
            for (int i = 0; i < childrenList.size(); i++) {
                TreeEntity.Node nd = childrenList.get(i);
                MenuEntity nodeData = getNodeData(nd);
                children.add(nodeData);
            }
        }
        menu.setChildren(children);
        return menu;
    }


}
