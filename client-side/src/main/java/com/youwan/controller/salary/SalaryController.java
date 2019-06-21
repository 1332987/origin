package com.youwan.controller.salary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youwan.common.utils.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/salary")
@Slf4j
public class SalaryController {

    @RequestMapping("/scheduling")
    public String scheduling(Model model) {
        log.info("scheduling");
        return "salary/scheduling";
    }

    @RequestMapping("/persons")
    public String persons(Model model) {
        log.info("persons");
        return "salary/persons";
    }

    @RequestMapping("/classess")
    public String classess(Model model) {
        log.info("classess");
        return "salary/classess";
    }

    @ResponseBody
    @RequestMapping("/getCheck")
    public String getCheck(Model model) {
        log.info("查询考勤表");
        Map<String, Object> data = new HashMap<>(10);
        data.put("pass", "12345678");
        data.put("personId", "-1");
        data.put("length", "-1");
        data.put("index", "0");
        data.put("startTime", "0");
        data.put("endTime", "0");
        String date = HttpUtil.post("http://192.168.1.7:8090/findRecords", data, 2000);
        JSONObject jsonObject = JSON.parseObject(date);
        JSONObject records = jsonObject.getJSONObject("data");
        log.info(records.toJSONString());
        records.remove("pageInfo");
        log.info(records.toJSONString());
        String datess = StringUtils.strip(records.toJSONString(), "{\"records\":");
        datess = StringUtils.strip(datess, "}");
        log.info(datess);
        return datess;
    }
}
