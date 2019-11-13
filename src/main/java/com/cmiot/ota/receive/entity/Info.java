package com.cmiot.ota.receive.entity;

import lombok.Data;

/**
 * Created by zhuocongbin
 * date 2019/11/13
 */
@Data
public class Info {
    // 用户id，OneNET平台分配给每一个注册用户唯一识别号
    private Long uid;
    // 请求发送时间
    private Long date;
}
