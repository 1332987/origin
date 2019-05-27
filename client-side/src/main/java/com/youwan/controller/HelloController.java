package com.youwan.controller;

import com.alibaba.fastjson.JSON;
import com.youwan.common.dao.PersonalInformationDao;
import com.youwan.common.dao.StaffDao;
import com.youwan.common.dao.UserDao;
import com.youwan.common.entity.PersonalInformation;
import com.youwan.common.entity.Staff;
import com.youwan.common.entity.User;
import com.youwan.common.utils.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class HelloController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private PersonalInformationDao personalInformationfDao;

        @RequestMapping("renlian")
        public String renlian (Model model){
            return "renlian";
        }

        @RequestMapping("personnels")
        public String personnels (Model model){
            return "personnel";
        }

        @RequestMapping("report")
        public String report (Model model){
            return "report";
        }

        @RequestMapping("welcome")
        public String welcome (Model model){
            return "welcome";
        }

        @RequestMapping("attendance")
        public String attendance (Model model){
//        HashMap<String, String> mData = new HashMap<String, String>();
//        mData.put("pass", "12345678");
//        mData.put("personId", "-1");
//        mData.put("length", "110");
//        mData.put("index", "0");
//        mData.put("startTime", "0");
//        mData.put("endTime", "0");
//        String date = NetUtil.startGet("http://192.168.1.12:8090/findRecords" , mData);
//        System.out.println(date);
//        JSONObject jobj = JSON.parseObject(date);
//        JSONObject jsons = jobj.getJSONObject("data");
//        JSONArray ja = jsons.getJSONArray("records");
//        List<PersonalInformation> listP = JSONObject.parseArray(ja.toJSONString(), PersonalInformation.class);
//        for(PersonalInformation e : listP){
//            personalInformationfDao.save(e);
//        }
//        model.addAttribute("data",jsons.getJSONArray("listP"));
            List<PersonalInformation> listP = personalInformationfDao.findAll();
            model.addAttribute("data", listP);
            return "attendance";
        }

        @ResponseBody
        @RequestMapping("serialNumber")
        public String serialNumber () {
            Map<String, Object> data = new HashMap<>();
            data.put("pass", "12345678");
            String date = NetUtil.post("http://192.168.1.12:8090/getDeviceKey", data);
            System.out.println(date);
            return date;
        }

        @ResponseBody
        @RequestMapping("openDoor")
        public String openDoor () {
            Map<String, Object> data = new HashMap<>();
            data.put("pass", "12345678");
            String date = NetUtil.post("http://192.168.1.12:8090/device/openDoorControl", data);
            System.out.println(date);
            return date;
        }

        @ResponseBody
        @RequestMapping("hairpin")
        public String hairpin () {
            HashMap<String, String> mData = new HashMap<String, String>();
            mData.put("pass", "12345678");
            mData.put("id", "88888");
            String date = NetUtil.startGet("http://192.168.1.12:8090/person/find", mData);
            System.out.println(date);
            return date;
        }

        @ResponseBody
        @RequestMapping("addStaff")
        public String addStaff (Staff sta){
            System.out.println(sta);
            HashMap<String, Object> data = new HashMap<>();
            data.put("pass", "12345678");
            data.put("person", JSON.toJSONString(sta));
            String date = NetUtil.post("http://192.168.1.12:8090/person/create", data);
            System.out.println(date);
            staffDao.save(sta);
            return date;
        }

        @ResponseBody
        @RequestMapping("addImg")
        public String addImg () {
            Map<String, Object> data = new HashMap<>();
            data.put("pass", "12345678");
            data.put("personId", "3211233");
            String date = NetUtil.post("http://192.168.1.12:8090/face/takeImg", data);
            System.out.println(date);
            return date;
        }

        @ResponseBody
        @RequestMapping("opDataList")
        public String opDataList () {
            Map<String, Object> data = new HashMap<>();
            data.put("id", "3");
            data.put("dataL", "[{\"idCardType\":\"1\",\"idCardNumber\":\"132456\",\"date\":\"2019-05-05 11:21:22\"},{\"idCardType\":\"01\",\"idCardNumber\":\"420683\",\"date\":\"2019-05-06 11:21:22\"}]");
            String date = NetUtil.post("http://127.0.0.1:8088/worker/workersAttendance/opDataList", data);
            System.out.println(date);
            return date;
        }
    }