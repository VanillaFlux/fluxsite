package com.vanillaflux.site.utils;

import com.quiptmc.core.QuiptIntegration;
import com.quiptmc.core.config.ConfigManager;
import com.quiptmc.core.sql.SqlDatabase;
import com.quiptmc.core.sql.SqlUtils;
import com.vanillaflux.site.config.ApiConfig;
import com.vanillaflux.site.managers.ServerManager;
import com.vanillaflux.site.managers.UptimeManager;

public class Utils {

    public final ApiConfig config;
    public final ServerManager serverManager;
    public final UptimeManager uptimeManager;

    public Utils(QuiptIntegration integration) {
        config = ConfigManager.registerConfig(integration, ApiConfig.class);
        //todo URGENT DO NOT PUSH BEFORE CHANGING THIS RAW PASSWORD --->
        integration.log("Utils", "Creating database...");
        SqlUtils.createDatabase("core", new SqlDatabase(SqlUtils.SQLDriver.MYSQL, "sql.vanillaflux.com",  3306, "vanillaflux", "sys", "9gGKGqthQJ&!#DGd"));
        serverManager = new ServerManager(integration);
        uptimeManager = new UptimeManager(integration);
        integration.enable();
    }


}
