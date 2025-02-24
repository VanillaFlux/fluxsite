package com.vanillaflux.site.components.controllers;

import com.quiptmc.core.sql.SqlDatabase;
import com.quiptmc.core.sql.SqlUtils;
import com.vanillaflux.site.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberController {


    @GetMapping("/member/{member}")
    public String home(@PathVariable String member, Model model) {
        Map<UUID, String> usernameMap = new HashMap<>();
        JSONObject users = new JSONObject();
        SqlDatabase database = SqlUtils.getDatabase("core");

        long inactiveTime = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(92, TimeUnit.DAYS);

        ResultSet rs = database.query("SELECT * FROM users WHERE username='" + member + "'");
        try {
            boolean foundUser = false;
            while (rs.next()) {
                foundUser = true;

            }
            if(!foundUser){
                model.addAttribute("error", "Member not found");
                return "members/index";
            }


        } catch (SQLException ex) {
            model.addAttribute("error", "Couldn't connect to VF API");

        }

//
//
//        model.addAttribute("members", members);
//        model.addAttribute("jumps", totalJumps);
        model.addAttribute("uptime", Main.utils.uptimeManager.getUptime());
//        model.addAttribute("hardWorkers", 25);


        model.addAttribute("data", users);
        model.addAttribute("usernameMap", usernameMap);
        return "members/index";
    }
}
