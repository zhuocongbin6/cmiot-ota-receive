package com.cmiot.ota.receive.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by zhuocongbin
 * date 2019/11/13
 */
@Data
public class Info {
    // 用户id，OneNET平台分配给每一个注册用户唯一识别号
    private Long uid;
    private Long pid;

    // 请求发送时间
    private Long at;
    private List<Long> dids;
    private Integer type;
    private Long startTime;
    private String version;

}
