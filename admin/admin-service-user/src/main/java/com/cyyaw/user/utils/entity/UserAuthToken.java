package com.cyyaw.user.utils.entity;

import com.cyyaw.user.table.entity.UUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author why
 */
@Data
@ToString
@NoArgsConstructor
public class UserAuthToken implements Serializable {
    /**
     * jwt令牌
     */
    private String jwtToken;

    /**
     * 用户信息
     */
    private UUser uUser;

}
