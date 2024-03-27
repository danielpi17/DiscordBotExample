package com.dan.bot.dataintegrators.enums;

public enum ServerPermissions {
    NA(0),
    ADMINISTRATOR(2),
    BAN(4),
    KICK(8),
    TIMEOUT(16),
    TIMEOUT_REMOVE(32),
    BAN_REMOVE(64),
    DISCORD_PUNISHMENTS(128),
    CLEAR(256),
    SLOWMODE(512),
    LOCKDOWN(1024),
    ROLE_FAMILY_MANAGEMENT(2048),
    RESPONSE_COMMAND(4096),
    TRUST_ADMIN(8192),
    FIVEM_UNBAN(16384),
    FIVEM_PUNISHMENTS(32768);

    public final long i;
    ServerPermissions(long i) {
        this.i = i;
    }
}
