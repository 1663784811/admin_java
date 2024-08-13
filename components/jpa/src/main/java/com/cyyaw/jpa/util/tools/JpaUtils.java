package com.cyyaw.jpa.util.tools;

import com.cyyaw.jpa.util.entity.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class JpaUtils {


    /**
     * 获取PageRequest  =====     分页
     */
    public static PageRequest getPageRequest(Integer page, Integer size) {
        return getPageRequest(page, size, null);
    }

    /**
     * 获取PageRequest  =====     分页
     */
    public static PageRequest getPageRequest(Integer page, Integer size, Sort sort) {
        PageRequest pageRequest;
        page = page == null ? 1 : page;
        size = size == null ? 20 : size;
        if (null != sort) {
            pageRequest = PageRequest.of(page - 1, size, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, size);
        }
        return pageRequest;
    }


    /**
     * 获取Sort  ========  排序
     */
    public static Sort getSort(String sortStr) {
        Sort sort = null;
        if (null != sortStr) {
            String[] sortArr = sortStr.split(",");
            // 判断是否有这个字段
            for (int i = 0; i < sortArr.length; i++) {
                String itemStr = sortArr[i];
                int lasted = itemStr.lastIndexOf("_desc");
                if (lasted == -1) {
                    if (null == sort) {
                        sort = Sort.by(Sort.Direction.ASC, itemStr);
                    } else {
                        sort.and(Sort.by(Sort.Direction.ASC, itemStr));
                    }
                } else {
                    String subStr = itemStr.substring(0, lasted);
                    if (null == sort) {
                        sort = Sort.by(Sort.Direction.DESC, subStr);
                    } else {
                        sort.and(Sort.by(Sort.Direction.DESC, subStr));
                    }
                }
            }
        }
        return sort;
    }


    public static <T extends BaseEntity> List<String> getTidList(List<T> list) {
        List<String> rest = new ArrayList<>();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                rest.add(list.get(i).getTid());
            }
        }
        return rest;
    }

}
