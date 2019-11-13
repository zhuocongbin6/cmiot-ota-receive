package com.cmiot.ota.receive.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by zhuocongbin
 * date 2019/11/13
 */
@Data
public class Message {
    private Long startTime;
    private List<Long> dids;
    private String version;
}
