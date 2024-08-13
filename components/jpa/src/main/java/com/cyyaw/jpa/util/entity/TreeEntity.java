package com.cyyaw.jpa.util.entity;

public interface TreeEntity<D> extends BaseEntity<D>{

    String getPid();

    void setPid(String pid);

    String getName();

    void setName(String name);

    String getTreeCode();

    void setTreeCode(String treeCode);


}
