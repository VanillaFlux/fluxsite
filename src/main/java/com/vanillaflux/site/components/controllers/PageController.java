package com.vanillaflux.site.components.controllers;

import com.quiptmc.core.sql.SqlDatabase;
import com.quiptmc.core.sql.SqlUtils;
import com.vanillaflux.site.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class PageController {


    @GetMapping("/")
    public String home(Model model) {

//        int members = 0;
//        int atotalJumps = 0;
        Map<UUID, String> usernameMap = new HashMap<>();
        JSONObject users = new JSONObject();
        SqlDatabase database = SqlUtils.getDatabase("core");

        long inactiveTime = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(92, TimeUnit.DAYS);

        ResultSet rs = database.query("SELECT * FROM users");
        try {
            while (rs.next()) {
                JSONObject user = new JSONObject();
                String username = rs.getString("username");
                UUID uid = UUID.fromString(rs.getString("uuid"));
                user.put("username", username);
                user.put("uuid", uid.toString());
                users.put(uid.toString(), user);
                usernameMap.put(uid, username);
                if (Long.parseLong(rs.getString("last_seen")) < inactiveTime) {
                    users.put("active_members", users.optInt("active_members", 0) + 1);
                }
                Blob blob = rs.getBlob("json");
                JSONObject json = new JSONObject(new String(blob.getBytes(1, (int) blob.length())));
                if (json.has("sessions")) {
                    JSONArray sessions = json.getJSONArray("sessions");
                    for (int i = 0; i < sessions.length(); i++) {
                        JSONObject session = sessions.getJSONObject(i);
                        users.put("total_jumps", users.optInt("total_jumps", 0) + session.optInt("jumps",0));
                        users.put("total_playtime", users.optLong("total_playtime", 0) + session.optLong("playtime"));
                        user.put("total_jumps", user.optInt("total_jumps", 0) + session.optInt("jumps",0));
                        user.put("total_playtime", user.optLong("total_playtime", 0) + session.optLong("playtime"));
                    }
                }
//                user.put("data", json);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println(users.toString(2));
//
//
//        model.addAttribute("members", members);
//        model.addAttribute("jumps", totalJumps);
        model.addAttribute("uptime", Main.utils.uptimeManager.getUptime());
//        model.addAttribute("hardWorkers", 25);


        model.addAttribute("data", users);
        model.addAttribute("usernameMap", usernameMap);
        return "index";
    }
}
