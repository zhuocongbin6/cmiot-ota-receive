package com.cmiot.ota.receive.controller;

import com.cmiot.ota.receive.entity.DataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuocongbin
 * date 2019/11/13
 */
@Slf4j
@RestController
public class ReceiverController {

    @Value("${ota.token}")
    private String token;
    @Value("${ota.uid}")
    private String uid;
    private static String PASSED = "PASSED";
    private static String FAILED = "FAILED";

    /**
     * OneNET页面配置URL是校验
     * @param signature
     * @return
     */
    @RequestMapping(value = "/ota/receive", method = RequestMethod.GET)
    public String check(String signature) {
        log.info("receive check: {}", signature);
        String md5 = getMD5();
        if (md5.equals(signature)) {
            return PASSED;
        }
        return FAILED;
    }

    /**
     * 接收OneNET推送的数据
     * @param dataInfo
     */
    @RequestMapping(value = "/ota/receive", method = RequestMethod.POST)
    public void receive(@RequestBody DataInfo dataInfo) {
        String md5 = getMD5();
        String signature = dataInfo.getSignature();
        if (md5.equals(signature)) {
            /**
             * 正常接收数据
             */
            log.info("receive data: dids={}, startTime={}, version={}", dataInfo.getMsg().getDids(), dataInfo.getMsg().getStartTime(), dataInfo.getMsg().getVersion());
        }else {
            /**
             * 数据异常，丢弃
             */
            log.error("receive data: dids={}, startTime={}, version={}", dataInfo.getMsg().getDids(), dataInfo.getMsg().getStartTime(), dataInfo.getMsg().getVersion());
        }
    }
    private String getMD5() {
        String base = token + uid;
        return DigestUtils.md5DigestAsHex(base.getBytes()).toLowerCase();
    }
}
