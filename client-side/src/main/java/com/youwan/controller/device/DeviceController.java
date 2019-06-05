package com.youwan.controller.device;

import com.alibaba.fastjson.JSON;
import com.youwan.common.dao.device.ManagementDao;
import com.youwan.common.entity.device.Management;
import com.youwan.common.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/device")
@Slf4j
public class DeviceController {
    @Autowired
    public ManagementDao managementDao;
    @RequestMapping("/management")
    public String management(Model model) throws InterruptedException {
        log.info("lailelaodi");
        List<String> lists = IPUtil.getIPs();
        List<Management> list = IPUtil.getMachine(lists);
        Thread.sleep(2000);
        return "device/management";
    }

    @ResponseBody
    @RequestMapping("/getManagement")
    public String getManagement(Model model) throws InterruptedException {
        log.info("lailelaodi");
        List<Management> list = managementDao.findAll();
        return JSON.toJSONString(list);
    }
}
