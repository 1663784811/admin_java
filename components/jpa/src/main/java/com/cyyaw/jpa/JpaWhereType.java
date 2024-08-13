package com.cyyaw.jpa;

/**
 * jpa 条件类型
 */
public enum JpaWhereType {
    like("lk", "%模糊查询%"),
    likeR("lkR", "模糊查询%"),
    likeL("lkL", "%模糊查询"),
    eq("eq", "等于"),
    neq("neq", "不等于"),
    geq("geq", "大于等于 >="),
    gt("gt", "大于 >"),
    leq("leq", "小于等于 <="),
    lt("lt", "小于 <"),
    in("in", "包含");

    JpaWhereType(String where, String note) {
        this.where = where;
        this.note = note;
    }

    /**
     * 条件
     */
    private String where;
    /**
     * 备注
     */
    private String note;

    public String getWhere() {
        return where;
    }
}
