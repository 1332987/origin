package com.youwan.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
                        list.add(StringUtils.trimToEmpty(str[0]));
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
    public static List<Map<String, String>> getMachine() {
        List<String> list = IPUtil.getIPs();
        log.info(list.toString());
        List<Map<String, String>> listM = new ArrayList<>();
        for (String s : list) {
            String date = NetUtil.post("http://" + s + ":8090/getDeviceKey", "");
            if (!StringUtils.isBlank(date)) {
                Map<String, String> map = new HashMap<>();
                JSONObject jsStr = JSONObject.parseObject(StringUtils.trimToEmpty(date));
                log.info(jsStr.toJSONString());
                map.put("ip", s);
                map.put("sn", jsStr.get("data").toString());
                map.put("pas", "12345678");
                map.put("model", "8090");
                listM.add(map);
                System.out.println(listM.size());
            }
        }
        return listM;
    }
}
