package com.youwan.controller.device;

import com.alibaba.fastjson.JSON;
import com.youwan.common.dao.device.ManagementDao;
import com.youwan.common.dao.device.ManagementDevDao;
import com.youwan.common.entity.device.Management;
import com.youwan.common.entity.device.ManagementDev;
import com.youwan.common.utils.IPUtil;
import com.youwan.common.utils.http.HttpUtil;
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
@RequestMapping("/device")
@Slf4j
public class DeviceController {
    @Autowired
    public ManagementDao managementDao;
    @Autowired
    public ManagementDevDao managementDevDao;

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

    @ResponseBody
    @RequestMapping("/updatePas")
    public String updatePas(String pas2, Management management) {
        log.info(management.toString());
        Map<String, Object> data = new HashMap<>();
        data.put("oldPass", management.getPas());
        management = managementDao.getOne(management.getId());
        data.put("newPass", pas2);
        String date = HttpUtil.post("http://" + management.getIp() + ":8090/setPassWord", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true")) {
            management.setPas(pas2);
            managementDao.save(management);
            return "修改密码成功";
        }
        return "修改密码失败";
    }

    @ResponseBody
    @RequestMapping("/setNetInfo")
    public String setNetInfo(Management management, ManagementDev managementDev) {
        log.info(management.toString());
        log.info(managementDev.toString());
        String ip = management.getIp();
        management = managementDao.getOne(management.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("pass", management.getPas());
        data.put("isDHCPMod", managementDev.getIsDHCPMod());
        data.put("ip", ip);
        data.put("gateway", managementDev.getGateway());
        data.put("subnetMask", managementDev.getSubnetMask());
        data.put("DNS", managementDev.getDns());
        String date = HttpUtil.post("http://" + ip + ":8090/setNetInfo", data, 2000);
        log.info("----" + date);
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true")) {
            ManagementDev anagem = managementDevDao.findByIp(management.getIp());
            if (anagem != null) {
                anagem.setIp(ip);
                anagem.setIsDHCPMod(managementDev.getIsDHCPMod());
                anagem.setGateway(managementDev.getGateway());
                anagem.setSubnetMask(managementDev.getSubnetMask());
                anagem.setDns(managementDev.getDns());
                managementDevDao.save(anagem);
            } else
                managementDevDao.save(managementDev);
            management.setIp(ip);
            managementDao.save(management);
            return JSON.parseObject(date).getString("msg");
        }
        return "修改网络配置失败";
    }

    @ResponseBody
    @RequestMapping("/setWifi")
    public String setWifi(ManagementDev managementDev) {
        log.info(managementDev.toString());
        Map<String, Object> data = new HashMap<>();
        data.put("wifiMsg", "12345678");
        String date = HttpUtil.post("http://" + managementDev.getIp() + ":8090/setWifi", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("msg");
        return "修改wifi配置失败";
    }

    @ResponseBody
    @RequestMapping("/setConfig")
    public String setConfig(Long id, ManagementDev m) {
        Management management = managementDao.getOne(id);
        log.info(m.toString());
        log.info(management.toString());
        Map<String, Object> data = new HashMap<>();
        data.put("pass", management.getPas());
        data.put("config", "{\"companyName\":\"" + m.getCompanyName() + "\",\"identifyDistance\":" + m.getIdentifyDistance()
                + ",\"identifyScores\":" + m.getIdentifyScores() + ",\"saveIdentifyTime\":" + m.getSaveIdentifyTime()
                + ",\"ttsModType\":" + m.getTtsModType() + ",\"ttsModContent\":\"" + m.getTtsModContent()
                + "\",\"comModType\":" + m.getComModType() + ",\"comModContent\":\"" + m.getComModContent()
                + "\",\"displayModType\":" + m.getDisplayModType() + ",\"displayModContent\":\"" + m.getDisplayModContent()
                + "\",\"slogan\":\"" + m.getSlogan() + "\",\"intro\":\"" + m.getIntro()
                + "\",\"recStrangerTimesThreshold\":" + m.getRecStrangerTimesThreshold() + ",\"recStrangerType\":" + m.getRecStrangerType()
                + ",\"ttsModStrangerType\":" + m.getTtsModStrangerType() + ",\"ttsModStrangerContent\":\"" + m.getTtsModStrangerContent()
                + "\",\"multiplayerDetection\"" + m.getMultiplayerDetection() + ",\"wg\":\"" + m.getWg() + "\",\"recRank\":" + m.getRecRank() + "}");
        String date = HttpUtil.post("http://" + management.getIp() + ":8090/setConfig", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true")) {
            ManagementDev anagem = managementDevDao.findByIp(management.getIp());
            if (anagem != null) {
                anagem.setCompanyName(m.getCompanyName());
                anagem.setIdentifyDistance(m.getIdentifyDistance());
                anagem.setIdentifyScores(m.getIdentifyScores());
                anagem.setSaveIdentifyTime(m.getSaveIdentifyTime());
                anagem.setTtsModType(m.getTtsModType());
                anagem.setTtsModContent(m.getTtsModContent());
                anagem.setComModType(m.getComModType());
                anagem.setComModContent(m.getComModContent());
                anagem.setDisplayModType(m.getDisplayModType());
                anagem.setDisplayModContent(m.getDisplayModContent());
                anagem.setSlogan(m.getSlogan());
                anagem.setIntro(m.getIntro());
                anagem.setRecStrangerTimesThreshold(m.getRecStrangerTimesThreshold());
                anagem.setRecStrangerType(m.getRecStrangerType());
                anagem.setTtsModStrangerType(m.getTtsModStrangerType());
                anagem.setTtsModStrangerContent(m.getTtsModStrangerContent());
                anagem.setMultiplayerDetection(m.getMultiplayerDetection());
                anagem.setWg(m.getWg());
                anagem.setRecRank(m.getRecRank());
                managementDevDao.save(anagem);
            } else
                managementDevDao.save(m);
            return JSON.parseObject(date).getString("msg");
        }
        return "修改wifi配置失败";
    }
}
