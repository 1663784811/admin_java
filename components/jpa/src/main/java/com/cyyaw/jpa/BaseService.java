package com.cyyaw.jpa;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.cyyaw.jpa.util.entity.BaseEntity;
import com.cyyaw.jpa.util.entity.TreeEntity;
import com.cyyaw.jpa.util.entity.TreeResponseEntity;
import com.cyyaw.jpa.util.tools.JpaUtils;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;


@Slf4j
public abstract class BaseService<T, D> implements BaseTableService<T, D> {
    public abstract BaseDao getBaseDao();

    @Override
    public List<T> findAll(JSONObject json) {
        String sortStr = json.getStr("sort");
        Sort sort = JpaUtils.getSort(sortStr);
        Specification<T> sp = new JpaSpecification(json);
        if (null != sort) {
            return getBaseDao().findAll(sp, sort);
        } else {
            return getBaseDao().findAll(sp);
        }
    }

    public List<T> findByExample(T t) {
        Example<T> example = Example.of(t);
        return getBaseDao().findAll(example);
    }

    @Override
    public PageRespone<T> findPage(JSONObject json) {
        String sortStr = json.getStr("sort");
        Integer page = json.getInt("page");
        Integer size = json.getInt("size");
        Sort sort = JpaUtils.getSort(sortStr);
        PageRequest pa = JpaUtils.getPageRequest(page, size, sort);
        Specification<T> sp = new JpaSpecification(json);
        Page all = getBaseDao().findAll(sp, pa);
        PageRespone respone = new PageRespone();
        respone.setContent(all.getContent());
        respone.setTotal(all.getTotalElements());
        respone.setPage(all.getNumber() + 1);
        respone.setSize(all.getSize());
        respone.setTotalPage(all.getTotalPages());
        return respone;
    }

    @Override
    public T findId(D d) {
        return (T) getBaseDao().findByid(d);
    }

    public T findByTid(T t) {
        if (t instanceof BaseEntity) {
            try {
                BaseEntity tt = (BaseEntity) t;
                BaseEntity o = (BaseEntity) t.getClass().newInstance();
                o.setId(tt.getId());
                Example<T> example = Example.of(t);
                return (T) getBaseDao().findAll(example);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // =============================================    树
    @Override
    public List<T> findTree(JSONObject json) {
        List<T> all = findAll(json);
        //处理树
        TreeResponseEntity rest = new TreeResponseEntity();
        if (null != all && all.size() > 0) {
            T t = all.get(0);
            if (t instanceof TreeEntity) {
                for (T treeNode : all) {
                    TreeEntity treeObj = (TreeEntity) treeNode;
                    TreeResponseEntity.Node<T> node = new TreeResponseEntity.Node<T>();
                    node.setTid(treeObj.getTid());
                    node.setData(treeNode);
                    node.setTitle(treeObj.getName());
                    node.setPid(treeObj.getPid());
                    rest.add(node);
                }
            }
        }
        return rest.getRoot();
    }

    // 保存树
    @Override
    public T saveTree(T t) {
        if (t instanceof TreeEntity) {

            TreeEntity<D> baseObj = (TreeEntity) t;
            D id = baseObj.getId();
            if (ObjectUtils.isEmpty(id)) {
                //添加
                //查询父节点
                String pid = baseObj.getPid();
                if (StrUtil.isNotBlank(pid)) {
                    try {
                        TreeEntity treeEntity = (TreeEntity) t.getClass().newInstance();
                        treeEntity.setTid(pid);
                        T obj = findByTid((T) treeEntity);
                        // 查询子节点

                        // 保存子节点

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                baseObj.setCreateTime(new Date());
                baseObj.setTid(WhyStringUtil.getUUID());
                baseObj.setTreeCode("");
                log.info("添加:{}", baseObj);
                return (T) getBaseDao().save(baseObj);
            } else {
                //修改
                log.info("修改:{}", baseObj);


                TreeEntity cpObj = (TreeEntity) findId(id);
                Assert.notNull(cpObj, "操作失败！");
                BeanUtils.copyProperties(baseObj, cpObj);
                return (T) getBaseDao().save(cpObj);
            }
        } else {
            return save(t);
        }
    }

    @Override
    public void delTree(D id) {
        T treeObj = findId(id);
        if (null != treeObj && treeObj instanceof TreeEntity) {
            // 删除子菜单
            TreeEntity treeEntity = (TreeEntity) treeObj;
            String tid = treeEntity.getTid();
            // ==========================
            try {
                TreeEntity tp = (TreeEntity) treeObj.getClass().newInstance();
                tp.setPid(tid);
                Example<TreeEntity> example = Example.of(tp);
                List<TreeEntity> treeList = getBaseDao().findAll(example);
                if (null != treeList && treeList.size() > 0) {
                    for (int i = 0; i < treeList.size(); i++) {
                        TreeEntity t = treeList.get(i);
                        delTree((D) t.getId());
                    }
                }
            } catch (Exception e) {

            }
            // ==========================
            // 删除要删除的菜单
            getBaseDao().delete(treeObj);
        }
    }

    // ==============================================

    @Override
    public T save(T t) {
        if (t instanceof BaseEntity) {
            BaseEntity<D> baseObj = (BaseEntity) t;
            D id = baseObj.getId();
            if (ObjectUtils.isEmpty(id)) {
                //添加
                baseObj.setCreateTime(new Date());
                baseObj.setTid(WhyStringUtil.getUUID());
                log.info("添加:{}", baseObj);
                return (T) getBaseDao().save(baseObj);
            } else {
                //修改
                log.info("修改:{}", baseObj);
                T cpObj = findId(id);
                Assert.notNull(cpObj, "操作失败！");
                BeanUtils.copyProperties(baseObj, cpObj);
                return (T) getBaseDao().save(cpObj);
            }
        } else {
            return (T) getBaseDao().save(t);
        }
    }

    @Override
    public void del(D[] ds) {
        getBaseDao().deleteInBatch(getBaseDao().findByidIsIn(ds));
    }
}
