package com.youwan.controller.people;

import com.youwan.common.dao.people.ProjectTeamDao;
import com.youwan.common.entity.people.ProjectTeam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/people")
@Slf4j
public class PeopleController {

    @Resource
    public ProjectTeamDao projectTeamDao;

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
}
