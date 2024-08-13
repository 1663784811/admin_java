package com.cyyaw.mqtt.handle;

import lombok.Data;

import java.util.Map;


@Data
public class EventData {
    private String eventName;
    private Map<String, Object> data;
}
