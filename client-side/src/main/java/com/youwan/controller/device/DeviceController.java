package com.youwan.controller.device;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/device")
@Slf4j
public class DeviceController {

    @RequestMapping("/management")
    public String management(Model model) {
        log.info("lailelaodi");
        return "device/management";
    }
}
