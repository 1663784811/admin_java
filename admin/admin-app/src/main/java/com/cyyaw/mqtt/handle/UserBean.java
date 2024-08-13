package com.cyyaw.mqtt.handle;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserBean {

    @JsonFormat
    private String userId;
    @JsonFormat
    private String avatar;
}
