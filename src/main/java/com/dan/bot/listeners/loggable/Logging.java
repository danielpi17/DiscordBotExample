package com.dan.bot.listeners.loggable;

import com.dan.bot.ServerHandler;
import com.dan.bot.dataintegrators.Temporary;
import com.dan.bot.utils.EmbedTools;
import org.javacord.api.entity.auditlog.AuditLog;
import org.javacord.api.entity.auditlog.AuditLogActionType;
import org.javacord.api.entity.auditlog.AuditLogEntry;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageDeleteEvent;
import org.javacord.api.event.message.MessageEditEvent;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.event.server.role.UserRoleAddEvent;
import org.javacord.api.event.server.role.UserRoleRemoveEvent;
import org.javacord.api.event.user.UserChangeNicknameEvent;
import org.javacord.api.listener.message.MessageDeleteListener;
import org.javacord.api.listener.message.MessageEditListener;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;
import org.javacord.api.listener.server.role.UserRoleAddListener;
import org.javacord.api.listener.server.role.UserRoleRemoveListener;
import org.javacord.api.listener.user.UserChangeNicknameListener;

public class Logging implements MessageEditListener, MessageDeleteListener, UserChangeNicknameListener, ServerMemberJoinListener, ServerMemberLeaveListener, UserRoleAddListener, UserRoleRemoveListener {
    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        if(event.getMessageAuthor().get().asUser().get().isBot()) return;
        EmbedBuilder embed = EmbedTools.info(event.getServer().get().getIdAsString());
        embed.setTitle("Delete Logs")
                .addInlineField("ID", event.getMessage().get().getAuthor().getIdAsString())
                .addInlineField("User", event.getMessage().get().getAuthor().asUser().get().getMentionTag())
                .addInlineField("Channel", event.getMessage().get().getChannel().asServerTextChannel().get().getMentionTag())
                .addInlineField("Server", event.getMessage().get().getServer().get().getName())
                .addField("Message", "```" + event.getMessageContent().get() + "```");
        new ServerHandler(event.getServer().get().getIdAsString()).deletes.sendMessage(embed);
    }

    @Override
    public void onMessageEdit(MessageEditEvent event) {
        if(event.getMessageAuthor().asUser().get().isBot()) return;
        EmbedBuilder embed = EmbedTools.info(event.getServer().get().getIdAsString());
        embed.setTitle("Edit Logs")
                .addInlineField("ID", event.getMessage().getAuthor().getIdAsString())
                .addInlineField("User", event.getMessage().getAuthor().asUser().get().getMentionTag())
                .addInlineField("Channel", event.getMessage().getChannel().asServerTextChannel().get().getMentionTag())
                .addInlineField("Server", event.getMessage().getServer().get().getName())
                .addField("Old Message", "```" + event.getOldMessage().get().getContent() + "```")
                .addField("New Message", "```" + event.getMessageContent() + "```");
        new ServerHandler(event.getServer().get().getIdAsString()).edits.sendMessage(embed);
    }

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent event) {
        if(event.getUser().isBot()) return;
        EmbedBuilder embed = EmbedTools.info(event.getServer().getIdAsString());
        embed.setTitle("Join Logs")
                .addInlineField("ID", event.getUser().getIdAsString())
                .addInlineField("User", event.getUser().getIdAsString())
                .addInlineField("Server", event.getServer().getName());
        new ServerHandler(event.getServer().getIdAsString()).joinsleaves.sendMessage(embed);
    }

    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent event) {
        if(event.getUser().isBot()) return;
        EmbedBuilder embed = EmbedTools.info(event.getServer().getIdAsString());
        embed.setTitle("Leave Logs")
                .addInlineField("ID", event.getUser().getIdAsString())
                .addInlineField("User", event.getUser().getIdAsString())
                .addInlineField("Server", event.getServer().getName());
        new ServerHandler(event.getServer().getIdAsString()).joinsleaves.sendMessage(embed);
    }

    @Override
    public void onUserRoleAdd(UserRoleAddEvent event) {
        AuditLog log = event.getServer().getAuditLog(0).join();
        AuditLogEntry entry = log.getEntries().get(0);
        EmbedBuilder embed = EmbedTools.info(event.getServer().getIdAsString());
        Temporary.removeTempRole(event.getUser(), event.getRole());
        embed.setTitle("Role Add Logs")
                .addInlineField("ID", event.getUser().getIdAsString())
                .addInlineField("User", event.getUser().getIdAsString())
                .addInlineField("Server", event.getServer().getName())
                .addInlineField("Role", event.getRole().getMentionTag());
        if (entry.getType().equals(AuditLogActionType.MEMBER_ROLE_UPDATE))
            if(entry.getUser().join().isBot()) return;
        if (entry.getType().equals(AuditLogActionType.MEMBER_ROLE_UPDATE))
            embed.addInlineField("Assigned By", entry.getUser().join().getMentionTag());
        new ServerHandler(event.getServer().getIdAsString()).roles.sendMessage(embed);
    }

    @Override
    public void onUserRoleRemove(UserRoleRemoveEvent event) {
        AuditLog log = event.getServer().getAuditLog(0).join();
        AuditLogEntry entry = log.getEntries().get(0);
        EmbedBuilder embed = EmbedTools.info(event.getServer().getIdAsString());
        Temporary.removeTempRole(event.getUser(), event.getRole());
        embed.setTitle("Role Remove Logs")
                .addInlineField("ID", event.getUser().getIdAsString())
                .addInlineField("User", event.getUser().getIdAsString())
                .addInlineField("Server", event.getServer().getName())
                .addInlineField("Role", event.getRole().getMentionTag());
        if (entry.getType().equals(AuditLogActionType.MEMBER_ROLE_UPDATE))
            if(entry.getUser().join().isBot()) return;
        if (entry.getType().equals(AuditLogActionType.MEMBER_ROLE_UPDATE))
            embed.addInlineField("Assigned By", entry.getUser().join().getMentionTag());

        new ServerHandler(event.getServer().getIdAsString()).roles.sendMessage(embed);
    }
    @Override
    public void onUserChangeNickname(UserChangeNicknameEvent event) {
        if(event.getUser().isBot()) return;
        EmbedBuilder embed = EmbedTools.info(event.getServer().getIdAsString());
        embed.setTitle("Nickname Logs")
                .addInlineField("ID", event.getUser().getIdAsString())
                .addInlineField("User", event.getUser().getIdAsString())
                .addInlineField("Server", event.getServer().getName())
                .addInlineField("Old Nickname", event.getOldNickname().get())
                .addInlineField("New Nickname", event.getNewNickname().get());
        new ServerHandler(event.getServer().getIdAsString()).joinsleaves.sendMessage(embed);
    }
}
