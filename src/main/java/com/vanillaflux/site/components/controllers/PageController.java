package com.vanillaflux.site.components.controllers;

import com.vanillaflux.site.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {


    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("servers", Main.utils.serverManager.servers().size());
        model.addAttribute("requestsHandled", Main.utils.serverManager.requests().size());
        model.addAttribute("uptime", Main.utils.uptimeManager.getUptime());
        model.addAttribute("hardWorkers", 25);
        return "index";
    }
}
