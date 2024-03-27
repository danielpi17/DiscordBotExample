package com.dan.bot.dataintegrators.enums;

public enum NetworkPermissions {
    NA(0),
    ADMINISTRATOR(2),
    NETWORK_BAN(4),
    NETWORK_KICK(8),
    NETWORK_BAN_REMOVE(16),
    NETWORK_NICK(32),
    NETWORK_PUNISHMENTS(64),
    NETWORK_PERMISSIONS(128),
    NETWORK_RESPONSE(256);

    public final long i;
    NetworkPermissions(long i) {
        this.i = i;
    }
}
