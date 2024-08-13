package com.cyyaw.jpa.util.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 树对象
 */
@Data
public class TreeResponseEntity<T> {

    /**
     * 森林
     */
    private List<Node<T>> root = new ArrayList<>();

    /**
     * 添加数据
     */
    public void add(TreeResponseEntity.Node<T> node) {
        if (root.size() > 0) {
            String pid = node.getPid();
            if (StrUtil.isBlank(pid)) {
                root.add(node);
            } else {
                // 添加到节点
                boolean h = false;
                for (int i = 0; i < root.size(); i++) {
                    Node<T> tree = root.get(i);
                    boolean b = this.addTreeToForest(tree, node);
                    if (b) {
                        h = true;
                        break;
                    }
                }
                if (!h) {
                    root.add(node);
                }
                // 整理森林
                for (int i = root.size() - 1; i < 1; i++) {
                    Node<T> tNode = root.get(i);
                    String treePid = tNode.getPid();
                    if (StrUtil.isNotBlank(treePid)) {
                        int size = root.size();
                        Node<T> nexTreeNode = root.get(i - 1);
                        boolean b = this.addTreeToForest(nexTreeNode, tNode);
                        if (b) {
                            root.remove(i);
                        }
                    }
                }
            }
        } else {
            root.add(node);
        }
    }


    private boolean addTreeToForest(Node<T> tree, TreeResponseEntity.Node<T> node) {
        String treeId = tree.getTid();
        String pid = node.getPid();
        List<Node> children = tree.getChildren();
        if (treeId.equals(pid)) {
            children.add(node);
            return true;
        } else {
            for (int i = 0; i < children.size(); i++) {
                Node childNode = children.get(i);
                boolean b = addTreeToForest(childNode, node);
                return b;
            }
        }
        return false;
    }


    @Data
    public static class Node<S> {

        /**
         * 节点ID
         */
        private String tid;

        /**
         * 父级ID
         */
        private String pid;

        /**
         * 名称
         */
        private String title;

        /**
         * 子节点
         */
        private List<Node> children = new ArrayList<>();

        /**
         * 数据
         */
        private S data;

    }
}
