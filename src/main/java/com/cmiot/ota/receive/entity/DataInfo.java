package com.cmiot.ota.receive.entity;

import lombok.Data;

/**
 * Created by zhuocongbin
 * date 2019/11/13
 */
@Data
public class DataInfo {
    private Info info;
    private String signature;
    private Message msg;
}
