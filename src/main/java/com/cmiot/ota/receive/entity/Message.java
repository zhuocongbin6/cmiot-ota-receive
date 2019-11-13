package com.cmiot.ota.receive.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by zhuocongbin
 * date 2019/11/13
 */
@Data
public class Message {
    //升级开始时间或生效时间
    private Long startTime;
    //升级设备列表
    private List<Long> dids;
    //升级目标版本号
    private String version;
}
