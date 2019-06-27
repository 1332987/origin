package com.youwan.controller.people;

import com.sun.jna.Native;
import com.youwan.common.dao.device.ManagementDao;
import com.youwan.common.dao.people.PersonGetImgDao;
import com.youwan.common.dao.people.PersonInfoDao;
import com.youwan.common.dao.people.ProjectTeamDao;
import com.youwan.common.entity.people.PeopleGetImg;
import com.youwan.common.entity.people.PersonInfo;
import com.youwan.common.entity.people.ProjectTeam;
import com.youwan.common.utils.CLibrary;
import com.youwan.common.utils.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/people")
@Slf4j
public class PeopleController {

    @Resource
    public ProjectTeamDao projectTeamDao;

    @Resource
    public PersonInfoDao personInfoDao;
    @Resource
    public ManagementDao managementDao;
    @Resource
    public PersonGetImgDao peopleGetImgDao;

    @RequestMapping("/dictionaries")
    public String dictionaries(Model model) {

        return "people/dictionaries";
    }

    @RequestMapping("/management")
    public String management(Model model) {
        log.info("lailelaodi-7");
        return "people/management";
    }

    @RequestMapping("/team")
    public String team(Model model) {
        log.info("team");
        return "people/team";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        log.info("team");
        return "people/add";
    }

    @RequestMapping("/addPersonInfo")
    public String addPersonInfo(Model model) {
        log.info("team");
        return "people/addPersonInfo";
    }

    @ResponseBody
    @RequestMapping("/addTeam")
    public String addTeam(ProjectTeam projectTeam) {
        log.info("ProjectTeam");
        projectTeamDao.save(projectTeam);
        return "添加成功";
    }

    @ResponseBody
    @RequestMapping("/getAllTeam")
    public List<ProjectTeam> getAllTeam(Model model) {
        log.info("team");
        List<ProjectTeam> listP = projectTeamDao.findAll();
        return listP;
    }

    @ResponseBody
    @RequestMapping("/getPersonInfo")
    public List<PersonInfo> getPersonInfo(Model model) {
        log.info("team");
        List<PersonInfo> listP = personInfoDao.findAll();
        return listP;
    }

    @ResponseBody
    @RequestMapping("/addPInfo")
    public String addPInfo(PersonInfo projectTeam) {
        log.info("ProjectTeam");
        personInfoDao.save(projectTeam);
        return "添加成功";
    }

    @ResponseBody
    @RequestMapping("/takeImg")
    public String takeImg(String id) {
        log.info("ProjectTeam");
        Map<String, Object> data = new HashMap<>(10);
        data.put("pass", "12345678");
        data.put("personId", "3211233");
        String date = HttpUtil.post("http://192.168.1.7:8090/face/takeImg", data, 2000);
        log.info("----" + date);
        return "{\"msg\":\"成功\"}";
    }

    /**
     * deviceKey String 设备唯一标识码
     * personId String 人员 id
     * time String 时间戳
     * imgPath String 照片本地路径
     * faceId String 照片 id
     * ip String 设备 IP 地址
     * feature String 特征码
     * featureKey String
     *
     * @return
     */
    @RequestMapping("/postImg")
    public void postImg(HttpServletRequest request, PeopleGetImg pgi) {

        request.getServletContext().setAttribute("pgi", pgi);
        peopleGetImgDao.save(pgi);
        log.info("deviceKey:==-------------------------" + pgi.toString());

    }

    @RequestMapping("/getIC")
    @ResponseBody
    public String getID() {

        CLibrary INSTANCE = (CLibrary) Native.load("Termb", CLibrary.class);
        System.out.println(INSTANCE.CVR_InitComm(1001));

        System.out.println(INSTANCE.CVR_Authenticate());

        System.out.println(INSTANCE.CVR_Read_Content(1));
        ByteBuffer byteB = ByteBuffer.allocate(1024);
        IntBuffer pLen = IntBuffer.allocate(4);
        System.out.println(INSTANCE.GetPeopleSex(byteB, pLen) + "---" + byteB);
        Charset charset = Charset.forName("GBK");
        CharBuffer charBuffer = charset.decode(byteB);
        log.info(charBuffer.toString());
        System.out.println(INSTANCE.CVR_CloseComm());

        return "ok";
    }
}
