package com.cyyaw.user.utils.entity;

import com.cyyaw.user.table.entity.UUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author why
 */
@Data
@ToString
@NoArgsConstructor
public class AuthToken implements Serializable {
    /**
     * jwt令牌
     */
    private String jwtToken;

    /**
     * 用户信息
     */
    private UUser uUser;

    /**
     * 用户权限列表
     */
//    private List<UAuth> authList;

    /**
     * 角色列表
     */
//    private List<URole> roleList;

}
