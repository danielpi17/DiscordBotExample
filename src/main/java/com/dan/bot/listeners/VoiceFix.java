package com.dan.bot.listeners;

import com.dan.bot.ServerHandler;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberLeaveEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberLeaveListener;

public class VoiceFix implements ServerVoiceChannelMemberJoinListener, ServerVoiceChannelMemberLeaveListener {
    @Override
    public void onServerVoiceChannelMemberLeave(ServerVoiceChannelMemberLeaveEvent event) {
        if(new ServerHandler(event.getServer().getIdAsString()).fixScreenshareIssues) {
            event.getChannel().createUpdater().addPermissionOverwrite(event.getUser(), new PermissionsBuilder().setAllowed(PermissionType.CONNECT, PermissionType.VIEW_CHANNEL).build()).update();
        }
    }

    @Override
    public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent event) {
        if(new ServerHandler(event.getServer().getIdAsString()).fixScreenshareIssues) {
            event.getChannel().createUpdater().removePermissionOverwrite(event.getUser()).update();
        }
    }
}
