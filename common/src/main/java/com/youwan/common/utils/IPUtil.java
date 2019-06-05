package com.youwan.common.utils;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;
import com.youwan.common.entity.device.Management;
import com.youwan.common.utils.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IPUtil {

    /**
     * StringUtils.trimToEmpty去空格
     * 获取局域网内所有ip
     */
    public static List<String> getIPs() {
        List<String> list = new ArrayList<String>();
        boolean flag = false;
        int count = 0;
        Runtime r = Runtime.getRuntime();
        Process p;
        try {
            for (int i = 0; i <= 255; i++) {
                Runnable runnable = () -> {
                    Runtime r1 = Runtime.getRuntime();
                };

                Thread thread = new Thread(runnable);
                thread.start();
            }
            p = r.exec("arp -a");
            BufferedReader br = new BufferedReader(new InputStreamReader(p
                    .getInputStream(), "GBK"));
            String inline;
            while ((inline = br.readLine()) != null) {
                if (inline.indexOf("接口") > -1) {
                    flag = !flag;
                    if (!flag) {
                        //碰到下一个"接口"退出循环
                        break;
                    }
                }
                if (flag) {
                    count++;
                    if (count > 2) {
                        //有效IP
                        String[] str = inline.split(" {4}");
                        if (StringUtils.substring(StringUtils.trimToEmpty(str[0]), 0, 1).equals("1"))
                            list.add(StringUtils.trimToEmpty(str[0]));
                        log.info(StringUtils.trimToEmpty(str[0]));
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 获取局域网内机器
     *
     * @return
     */
    public static List<Management> getMachine(List<String> list) throws InterruptedException {
        log.info(list.toString());
        List<Management> listM = new ArrayList<>();
        for (String s : list) {
            ThreadUtil.execute(new Runnable() {
                public void run() {
                    String date = "";
                    try {
                        date = HttpUtil.post("http://" + s + ":8090/getDeviceKey", "", 2000);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    if (!StringUtils.isBlank(date)) {
                        Management map = new Management();
                        JSONObject jsStr = JSONObject.parseObject(StringUtils.trimToEmpty(date));
                        log.info("date:" + jsStr.toJSONString());
                        map.setIp(s);
                        map.setSn(jsStr.get("data").toString());
                        map.setPas("12345678");
                        map.setModel("8090");
                        listM.add(map);
                        log.info(list.size() + "map" + map.toString());
                    }
                }
            });
        }

        log.info("获取设备完毕");

        return listM;
    }
}
