package com.cmiot.ota.receive.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        String md5 = getChecekMD5();
        log.info("local md5: {}", md5);
        if (md5.equals(signature)) {
            return PASSED;
        }
        return FAILED;
    }

    /**
     * 接收OneNET推送的数据
     * @param data
     */
    @RequestMapping(value = "/ota/receive", method = RequestMethod.POST)
    public void receive(@RequestBody String data) {
        JSONObject dataInfo = new JSONObject(data);
        log.info("dataInfo: {}", dataInfo);
        String signature = dataInfo.getString("signature");
        JSONObject msg = dataInfo.getJSONObject("msg");
        String nonce = dataInfo.getString("nonce");
        String md5 = getDataMD5(nonce, msg.toString());
        if (md5.equals(signature)) {
            /**
             * 正常接收数据
             */
            log.info("data check success");
            print(msg);
        }else {
            /**
             * 数据异常，丢弃
             */
            log.info("data check failed");
            print(msg);
        }
    }
    private String getChecekMD5() {
        String base = token + uid;
        return DigestUtils.md5DigestAsHex(base.getBytes()).toLowerCase();
    }
    private String getDataMD5(String nonce, String msg) {
        String var = token + nonce + msg;
        //简化操作，单纯的求md5值即可
        return DigestUtils.md5DigestAsHex(var.getBytes()).toLowerCase();
    }
    private void print(JSONObject msg) {
        long uid = msg.getLong("uid");//用户id
        long pid = msg.getLong("pid");//产品id
        long at = msg.getLong("at");//数据推送的时间，毫秒
        List<Object> dids = (msg.getJSONArray("dids").toList());//推送信息关联的设备列表
        int type = msg.getInt("type");//推送信息类型,1:新任务，2:开始下载，3:下载成功，4:升级成功
        long startTime = 0;//type为1存在该字段,任务开始生效时间，毫秒
        if (msg.has("startTime")) {
            startTime = msg.getLong("startTime");
        }
        String version = null;// 1：代表任务的目标版本，4：代表升级成功后的设备版本，2,3不存在该字段
        if (msg.has("version")) {
            version = msg.getString("version");
        }
        log.info("receive data: dids={}, startTime={}, version={}, type={}, uid={}, pid={}, at={}",
                dids, startTime, version, type, uid, pid, at);
    }
}
