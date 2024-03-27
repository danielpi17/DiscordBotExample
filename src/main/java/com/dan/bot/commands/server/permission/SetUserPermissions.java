package com.dan.bot.commands.server.permission;

import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class SetUserPermissions extends DiscordSlashCmd {
    public SetUserPermissions() {
        super("set-user-permissions", "Set user permissions", ServerPermissions.ADMINISTRATOR,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User", true),
                SlashUtil.newArg(SlashCommandOptionType.LONG, "level", "Level", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        Long level = i.getArgumentLongValueByName("level").get();

        Permissions.setPermissionLevel(i.getServer().get().getIdAsString(), user, level);

        replyMemberOnly("", EmbedTools.success(i.getServer().get().getIdAsString()).setDescription("Successfully set " + user.getMentionTag() + " to permission " + level));
    }
}
