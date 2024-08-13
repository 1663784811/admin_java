package com.cyyaw.mqtt.entity;


import lombok.Data;

@Data
public class PwJson {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) GET_pwd_ACK
         */
        private String type;

        /**
         * 请求流水号 Int
         * 防止异步错误，将本次请求报文的值原样返回
         */
        private String txnNo;
    }

    @Data
    public static class Info {
        /**
         * 返回状态
         */
        private String result;

        /**
         * 新账号
         */
        private String accountAck;

        /**
         * 新密码
         */
        private String pwdAck;
    }


}
