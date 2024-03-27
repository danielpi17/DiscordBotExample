package com.dan.bot.listeners.loggable;

import com.dan.bot.ServerHandler;
import com.dan.bot.dataintegrators.PunishmentIntegration;
import org.javacord.api.entity.auditlog.AuditLog;
import org.javacord.api.entity.auditlog.AuditLogActionType;
import org.javacord.api.entity.auditlog.AuditLogEntry;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.event.user.UserChangeTimeoutEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;
import org.javacord.api.listener.user.UserChangeTimeoutListener;

public class PunishmentLogging implements ServerMemberBanListener, ServerMemberLeaveListener, UserChangeTimeoutListener {
    @Override
    public void onServerMemberBan(ServerMemberBanEvent event) {
        AuditLog log = event.getServer().getAuditLog(0).join();
        AuditLogEntry entry = log.getEntries().get(0);
        if (entry.getType().equals(AuditLogActionType.MEMBER_BAN_ADD))
            PunishmentIntegration.issuePunishment(
                    event.getUser().getIdAsString(),
                    entry.getUser().join().getIdAsString(),
                    PunishmentIntegration.Type.BAN,
                    entry.getReason().get(),
                    "Permanent",
                    event.getServer().getIdAsString(),
                    false
            );
    }

    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent event) {
        AuditLog log = event.getServer().getAuditLog(0).join();
        AuditLogEntry entry = log.getEntries().get(0);
        if (entry.getType().equals(AuditLogActionType.MEMBER_KICK) && event.getUser().equals(entry.getTarget().get().asUser().join()))
            PunishmentIntegration.issuePunishment(
                    event.getUser().getIdAsString(),
                    entry.getUser().join().getIdAsString(),
                    PunishmentIntegration.Type.KICK,
                    entry.getReason().get(),
                    "Permanent",
                    event.getServer().getIdAsString(),
                    false
            );

    }

    @Override
    public void onUserChangeTimeout(UserChangeTimeoutEvent event) {
        // needs work
    }
}
