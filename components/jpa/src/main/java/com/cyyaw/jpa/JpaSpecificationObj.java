package com.cyyaw.jpa;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jsonStr 格式
 */
@Slf4j
public class JpaSpecificationObj<T> implements Specification<T> {


    private Map<String, Object> whereList = new HashMap<>();


    public static <T> JpaSpecificationObj<T> getInstance() {
        return new JpaSpecificationObj<T>();
    }


    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<>();
        for (String key : whereList.keySet()) {
            int index = key.indexOf("_");
            if (index > 0) {
                Object o = whereList.get(key);
                String wheres = key.substring(0, index);
                String columns = key.substring(index + 1);
                Predicate predicate = predicateWhere(root, cb, wheres, columns, o);
                predicateList.add(predicate);
            }
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        return cb.and(predicateList.toArray(predicates));
    }


    public JpaSpecificationObj<T> like(String key, String value) {
        if (StrUtil.isNotBlank(value)) {
            return likeV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> likeV(String key, String value) {
        String keyStr = JpaWhereType.like.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }


    public JpaSpecificationObj<T> likeRight(String key, String value) {
        if (StrUtil.isNotBlank(value)) {
            return likeRightV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> likeRightV(String key, String value) {
        String keyStr = JpaWhereType.likeR.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    public JpaSpecificationObj<T> likeLeft(String key, String value) {
        if (StrUtil.isNotBlank(value)) {
            return likeLeftV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> likeLeftV(String key, String value) {
        String keyStr = JpaWhereType.likeL.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    public JpaSpecificationObj<T> eq(String key, Object value) {
        if (ObjUtil.isNotEmpty(value)) {
            return eqV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> eqV(String key, Object value) {
        String keyStr = JpaWhereType.eq.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    public JpaSpecificationObj<T> neq(String key, Object value) {
        if (ObjUtil.isNotEmpty(value)) {
            return neqV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> neqV(String key, Object value) {
        String keyStr = JpaWhereType.neq.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    public JpaSpecificationObj<T> geq(String key, Number value) {
        if (ObjUtil.isNotEmpty(value)) {
            return geqV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> geqV(String key, Number value) {
        String keyStr = JpaWhereType.geq.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    public JpaSpecificationObj<T> gt(String key, Number value) {
        if (ObjUtil.isNotEmpty(value)) {
            return gtV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> gtV(String key, Number value) {
        String keyStr = JpaWhereType.gt.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    public JpaSpecificationObj<T> leq(String key, Number value) {
        if (ObjUtil.isNotEmpty(value)) {
            return leqV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> leqV(String key, Number value) {
        String keyStr = JpaWhereType.leq.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    public JpaSpecificationObj<T> ltq(String key, Number value) {
        if (ObjUtil.isNotEmpty(value)) {
            return ltV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> ltV(String key, Number value) {
        String keyStr = JpaWhereType.lt.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    public JpaSpecificationObj<T> in(String key, String value) {
        if (ObjUtil.isNotEmpty(value)) {
            return inV(key, value);
        }
        return this;
    }

    public JpaSpecificationObj<T> inV(String key, String value) {
        String keyStr = JpaWhereType.in.getWhere() + "_" + key;
        whereList.put(keyStr, value);
        return this;
    }

    private Predicate predicateWhere(final Root root, final CriteriaBuilder cb, final String wheres, final String columns, Object value) {
        Predicate predicate = null;
        if (JpaWhereType.like.getWhere().equals(wheres)) {
            predicate = cb.like(root.get(columns).as(String.class), "%" + value + "%");
        } else if (JpaWhereType.likeL.getWhere().equals(wheres)) {
            predicate = cb.like(root.get(columns).as(String.class), "%" + value);
        } else if (JpaWhereType.likeR.getWhere().equals(wheres)) {
            predicate = cb.like(root.get(columns).as(String.class), value + "%");
        } else if (JpaWhereType.neq.getWhere().equals(wheres)) {
            predicate = cb.notEqual(root.get(columns), value);
        } else if (JpaWhereType.geq.getWhere().equals(wheres)) {
            predicate = cb.ge(root.get(columns), (Number) value);
        } else if (JpaWhereType.gt.getWhere().equals(wheres)) {
            predicate = cb.gt(root.get(columns), (Number) value);
        } else if (JpaWhereType.leq.getWhere().equals(wheres)) {
            predicate = cb.le(root.get(columns), (Number) value);
        } else if (JpaWhereType.lt.getWhere().equals(wheres)) {
            predicate = cb.lt(root.get(columns), (Number) value);
        } else if (JpaWhereType.in.getWhere().equals(wheres)) {
            CriteriaBuilder.In<Object> in = cb.in(root.get(columns));
            String[] split = value.toString().split(",");
            for (int i = 0; i < split.length; i++) {
                in.value(split[i]);
            }
            predicate = in;
        } else {
            predicate = cb.equal(root.get(columns), value);
        }
        return predicate;
    }
}
