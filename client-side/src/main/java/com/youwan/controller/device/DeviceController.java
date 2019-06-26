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
        log.info("management");
        List<String> lists = IPUtil.getIPs();
        List<Management> list = IPUtil.getMachine(lists);
        log.info("=--=-1=-=-=");
        Thread.sleep(2000);
        for (Management m : list) {

            if (managementDao.findByIp(m.getIp()) == null)
                managementDao.save(m);
            log.info(m.toString());
            log.info("=--=-=-=-=");
            ManagementDev md = managementDevDao.findByIp(m.getIp());
            log.info("=--=-2=-=-=");
            if (md == null) {
                md = new ManagementDev();
                log.info(m.getIp());
                md.setIp(m.getIp());
                log.info(md.toString());
                managementDevDao.save(md);
            }
        }

        log.info("=--=-3=-=-=");
        return "device/management";
    }

    @ResponseBody
    @RequestMapping("/getManagement")
    public String getManagement(Model model) throws InterruptedException {
        log.info("lailelaodi-5");
        List<Management> list = managementDao.findAll();
        return JSON.toJSONString(list);
    }

    @ResponseBody
    @RequestMapping("/updatePas")
    public String updatePas(String pas2, Management management) {
        log.info(management.toString());
        Map<String, Object> data = new HashMap<>();
        data.put("oldPass", management.getPas());
        management = managementDao.findByIp(management.getIp());
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
        management = managementDao.findByIp(management.getIp());
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
    public String setConfig(String ip, ManagementDev m) {
        Management management = managementDao.findByIp(ip);
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
                + "\",\"multiplayerDetection\":" + m.getMultiplayerDetection() + ",\"wg\":\"" + m.getWg() + "\",\"recRank\":" + m.getRecRank() + "}");
        String date = HttpUtil.post("http://" + management.getIp() + ":8090/setConfig", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        log.info(JSON.toJSONString(data));
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
            return JSON.parseObject(date).getString("data");
        }
        return "修改配置失败";
    }


    @ResponseBody
    @RequestMapping("/setIdentifyCallBack")
    public String setIdentifyCallBack(String ip, ManagementDev managementDev) {
        log.info(managementDev.toString());
        managementDev.setIp(managementDevDao.findByIp(ip).getIp());
        Management m = managementDao.findByIp(ip);
        Map<String, Object> data = new HashMap<>();
        data.put("pass", m.getPas());
        data.put("callbackUrl", managementDev.getCallbackUrl());
        String date = HttpUtil.post("http://" + managementDev.getIp() + ":8090/setIdentifyCallBack", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("data");

        data.remove("callbackUrl");
        data.put("url", managementDev.getUrl());
        date = HttpUtil.post("http://" + managementDev.getIp() + ":8090/setDeviceHeartBeat", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("data");

        data.put("url", managementDev.getUrlImg());
        date = HttpUtil.post("http://" + managementDev.getIp() + ":8090/setImgRegCallBack", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("data");
        return "设置失败";
    }

    @ResponseBody
    @RequestMapping("/setScreenOrt")
    public String setScreenOrt(String ip, Integer orientation) {
        log.info(ip);
        Management m = managementDao.findByIp(ip);
        Map<String, Object> data = new HashMap<>();
        data.put("pass", m.getPas());
        data.put("orientation", orientation);
        String date = HttpUtil.post("http://" + m.getIp() + ":8090/setScreenOrt", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("msg");
        return "修改配置失败";
    }

    @ResponseBody
    @RequestMapping("/changeLogo")
    public String changeLogo(String ip, String imgBase64) {
        log.info(ip);
        log.info(imgBase64);
        imgBase64 = StringUtils.remove(imgBase64, "data:image/jpg;base64,");
        imgBase64 = StringUtils.remove(imgBase64, "data:image/png;base64,");
        imgBase64 = StringUtils.remove(imgBase64, "data:image/jpeg;base64,");
        Management m = managementDao.findByIp(ip);
        Map<String, Object> data = new HashMap<>();
        data.put("pass", m.getPas());
        data.put("imgBase64", imgBase64);
        String date = HttpUtil.post("http://" + m.getIp() + ":8090/changeLogo", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("msg");
        return "修改配置失败";
    }

    @ResponseBody
    @RequestMapping("/getDeviceKey")
    public String getDeviceKey(String ip) {
        log.info(ip);
        Management m = managementDao.findByIp(ip);
        String date = HttpUtil.post("http://" + m.getIp() + ":8090/getDeviceKey", "", 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("data");
        return "获取设备序列号失败";
    }

    @ResponseBody
    @RequestMapping("/setTime")
    public String setTime(String ip, String timestamp) {
        log.info(ip);
        Management m = managementDao.findByIp(ip);
        Map<String, Object> data = new HashMap<>();
        data.put("pass", m.getPas());
        data.put("timestamp", timestamp);
        String date = HttpUtil.post("http://" + m.getIp() + ":8090/setTime", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("msg");
        return "获取设备序列号失败";
    }

    @ResponseBody
    @RequestMapping("/restartDevice")
    public String restartDevice(String ip) {
        log.info(ip);
        Management m = managementDao.findByIp(ip);
        Map<String, Object> data = new HashMap<>();
        data.put("pass", m.getPas());
        String date = HttpUtil.post("http://" + m.getIp() + ":8090/restartDevice", data, 2000);
        log.info("----" + date);
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return "成功";
        return "重启设备序失败";
    }

    @ResponseBody
    @RequestMapping("/deviceReset")
    public String deviceReset(String ip, Boolean delete) {
        log.info(ip);
        Management m = managementDao.findByIp(ip);
        Map<String, Object> data = new HashMap<>();
        data.put("pass", m.getPas());
        data.put("delete", delete);
        String date = HttpUtil.post("http://" + m.getIp() + ":8090/device/reset", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("msg");
        return "获取设备序列号失败";
    }

    @ResponseBody
    @RequestMapping("/deviceOpenDoorControl")
    public String deviceOpenDoorControl(String ip, Boolean delete) {
        log.info(ip);
        Management m = managementDao.findByIp(ip);
        Map<String, Object> data = new HashMap<>();
        data.put("pass", m.getPas());
        String date = HttpUtil.post("http://" + m.getIp() + ":8090/device/openDoorControl", data, 2000);
        log.info("----" + date);
        log.info("----" + JSON.parseObject(date).getString("success"));
        if (StringUtils.equals(JSON.parseObject(date).getString("success"), "true"))
            return JSON.parseObject(date).getString("msg");
        return "获取设备序列号失败";
    }
}
