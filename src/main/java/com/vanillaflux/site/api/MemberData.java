package com.vanillaflux.site.api;

import java.util.UUID;

public record MemberData(UUID uuid, String username, long lastSeen) {
}
