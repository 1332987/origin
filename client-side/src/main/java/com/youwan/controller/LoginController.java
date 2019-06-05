package com.youwan.controller;

import com.youwan.common.dao.UserDao;
import com.youwan.common.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserDao userDao;


    @RequestMapping("indexs")
    @ResponseBody
    public String indexs(Model model, String username, String password) {
        User use = userDao.findByUsername(username);
        if (use == null)
            return "账号错误";
        else if (!StringUtils.equals(password, use.getPassword()))
            return "密码错误";

        log.info("password" + StringUtils.equals(password, use.getPassword()));
        return "登陆成功";
    }

    @RequestMapping("index")
    public String index(Model model) {
        Runtime run = Runtime.getRuntime();
        for (int i = 0; i <= 224; i++) {
            String command = "ping -n 1  -w 30 192.168.1." + i;
            Runnable runnable = () -> {
                try {
                    run.exec(command);
//                    log.info("command："+command);
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        return "index";
    }
}
