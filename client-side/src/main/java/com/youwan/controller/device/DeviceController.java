package com.youwan.controller.device;

import com.youwan.common.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/device")
@Slf4j
public class DeviceController {

    @RequestMapping("/management")
    public String management(Model model) {
        log.info("lailelaodi");
        List<Map<String, String>> list = IPUtil.getMachine();

        return "device/management";
    }
}
