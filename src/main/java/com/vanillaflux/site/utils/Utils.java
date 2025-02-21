package com.vanillaflux.site.utils;

import com.quiptmc.core.QuiptIntegration;
import com.quiptmc.core.config.ConfigManager;
import com.vanillaflux.site.config.ApiConfig;
import com.vanillaflux.site.managers.ServerManager;
import com.vanillaflux.site.managers.UptimeManager;

public class Utils {

    public final ApiConfig config;
    public final ServerManager serverManager;
    public final UptimeManager uptimeManager;

    public Utils(QuiptIntegration integration) {
        config = ConfigManager.registerConfig(integration, ApiConfig.class);
        serverManager = new ServerManager(integration);
        uptimeManager = new UptimeManager(integration);
        integration.enable();
    }


}
