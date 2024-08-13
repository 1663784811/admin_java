package com.cyyaw.mqtt.entity;

import lombok.Data;

@Data
public class SwChangeJson {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) cirAuth
         */
        private String type;
        /**
         * 请求流水号 int防止异步错误，取值为当前毫秒级时间戳
         */
        private String txnNo;
    }


    @Data
    public static class Info {
        /**
         * 返回状态 Enumresult true/flase
         */
        private String result;
        /**
         * 失败原因 char[msg_LEN]
         */
        private String msg;
    }


}
