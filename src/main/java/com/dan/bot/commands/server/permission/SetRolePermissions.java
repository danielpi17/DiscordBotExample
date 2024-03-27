package com.dan.bot.commands.server.permission;

import com.dan.bot.commands.server.rolepermissions.SetRoleChild;
import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class SetRolePermissions extends DiscordSlashCmd {
    public SetRolePermissions() {
        super("set-role-permissions", "Set role permissions", ServerPermissions.ADMINISTRATOR,
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "role", "Role", true),
                SlashUtil.newArg(SlashCommandOptionType.LONG, "level", "Level", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        Role role = i.getArgumentRoleValueByName("role").get();
        Long level = i.getArgumentLongValueByName("level").get();

        Permissions.setPermissionLevel(i.getServer().get().getIdAsString(), role, level);

        replyMemberOnly("", EmbedTools.success(i.getServer().get().getIdAsString()).setDescription("Successfully set " + role.getMentionTag() + " to permission " + level));
    }
}
