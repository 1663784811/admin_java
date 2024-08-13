package com.cyyaw.signin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cyyaw.config.exception.WebException;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.signin.service.SiSignLogService;
import com.cyyaw.signin.table.dao.SiSignInDao;
import com.cyyaw.signin.table.dao.SiSignLogDao;
import com.cyyaw.signin.table.entity.SiSignIn;
import com.cyyaw.signin.table.entity.SiSignLog;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class SiSignLogServiceImpl extends BaseService<SiSignLog, Integer> implements SiSignLogService {

    @Autowired
    private SiSignLogDao siSignLogDao;

    @Autowired
    private SiSignInDao siSignInDao;

    @Override
    public BaseDao getBaseDao() {
        return siSignLogDao;
    }

    @Override
    public List<SiSignLog> findBySignInId(String signInId) {
        if (StrUtil.isNotBlank(signInId)) {
            SiSignLog signLog = new SiSignLog();
            signLog.setSignInId(signInId);
            Example<SiSignLog> example = Example.of(signLog);
            return siSignLogDao.findAll(example);
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public SiSignLog save(SiSignLog signLog) {
        String signInId = signLog.getSignInId();
        String name = signLog.getName();
        if (StrUtil.isBlank(name)) {
            WebException.fail("请输入姓名");
        }
        SiSignIn siSignIn = siSignInDao.findByTid(signInId);
        if (null == siSignIn) {
            WebException.fail("活动不存在");
        }
        // 第一步： 查询某人是否已经签到
        List<SiSignLog> logList = siSignLogDao.findAllBySignInIdAndName(signInId, name);
        if (logList.size() > 0) {
            SiSignLog sign = logList.get(0);
            Integer status = sign.getStatus();
            if (null != status && status.equals(2)) {
                WebException.fail("您已经签到");
            } else {
                // 未签到, 修改为签到
                sign.setStatus(2);
                Integer otherSign = signLog.getOtherSign();
                if (null != otherSign && otherSign.equals(1)) {
                    // 帮签
                    sign.setOtherSign(1);
                } else {
                    sign.setOtherSign(0);
                }
                return siSignLogDao.save(sign);
            }
        }
        // 第二步： 新签到
        signLog.setTid(WhyStringUtil.getUUID());
        signLog.setCreateTime(new Date());
        signLog.setAppId(siSignIn.getAppId());
        signLog.setStatus(2);
        signLog.setDel(0);
        if (signLog.getOtherSign() == null) {
            signLog.setOtherSign(0);
        }
        signLog.setAppoint(0);
        return siSignLogDao.save(signLog);
    }
}

