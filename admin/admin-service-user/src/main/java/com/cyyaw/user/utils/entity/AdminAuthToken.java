package com.cyyaw.user.utils.entity;

import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.table.entity.TRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author why
 */
@Data
@ToString
@NoArgsConstructor
public class AdminAuthToken implements Serializable {
    /**
     * jwt令牌
     */
    private String jwtToken;

    /**
     * 用户信息
     */
    private TAdmin tAdmin;

    /**
     * 用户权限列表
     */
    private List<TPower> authList;

    /**
     * 角色列表
     */
    private List<TRole> roleList;

}
