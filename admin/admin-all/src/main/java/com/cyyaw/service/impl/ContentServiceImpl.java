package com.cyyaw.service.impl;

import com.cyyaw.content.table.dao.CttCommentDao;
import com.cyyaw.content.table.dao.CttContentDao;
import com.cyyaw.content.table.entity.CttContent;
import com.cyyaw.jpa.JpaSpecificationObj;
import com.cyyaw.jpa.util.tools.JpaUtils;
import com.cyyaw.service.ContentService;
import com.cyyaw.user.table.dao.UUserDao;
import com.cyyaw.user.table.entity.UUser;
import com.cyyaw.util.entity.ContentResponse;
import com.cyyaw.util.tools.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {


    @Autowired
    private CttCommentDao cttCommentDao;

    @Autowired
    private CttContentDao cttContentDao;

    @Autowired
    private UUserDao uUserDao;


    @Override
    public BaseResult<List<ContentResponse>> findContent(String appId, Integer page, Integer size) {
        JpaSpecificationObj where = JpaSpecificationObj.getInstance().eq("appId", appId);
        PageRequest pageRequest = JpaUtils.getPageRequest(page, size);
        Page<CttContent> contentPage = cttContentDao.findAll(where, pageRequest);
        List<CttContent> content = contentPage.getContent();
        List<String> userIdList = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            CttContent cttContent = content.get(i);
            userIdList.add(cttContent.getUserId());
        }
        List<UUser> uUserList = uUserDao.findByTidIn(userIdList);
        List<ContentResponse> rest = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            CttContent cttContent = content.get(i);
            ContentResponse ct = new ContentResponse();
            ct.setTid(cttContent.getTid());
            ct.setContentText(cttContent.getContent());
            ct.setContent(cttContent);
            String userId = cttContent.getUserId();
            for (int j = 0; j < uUserList.size(); j++) {
                UUser uUser = uUserList.get(j);
                if (uUser.getTid().equals(userId)) {
                    ct.setUserName(uUser.getNickName());
                    ct.setUserId(uUser.getTid());
                    ct.setFace(uUser.getFace());
                    ct.setUser(uUser);
                    break;
                }
            }
            rest.add(ct);
        }
        return BaseResult.ok(rest);
    }


}
