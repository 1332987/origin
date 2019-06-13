package com.youwan.controller.people;

import com.youwan.common.dao.device.ManagementDao;
import com.youwan.common.dao.people.PersonInfoDao;
import com.youwan.common.dao.people.ProjectTeamDao;
import com.youwan.common.entity.people.PersonInfo;
import com.youwan.common.entity.people.ProjectTeam;
import com.youwan.common.utils.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @RequestMapping("/dictionaries")
    public String dictionaries(Model model) {
        log.info("lailelaodi");
        return "people/dictionaries";
    }

    @RequestMapping("/management")
    public String management(Model model) {
        log.info("lailelaodi");
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
        return "添加成功";
    }
}
