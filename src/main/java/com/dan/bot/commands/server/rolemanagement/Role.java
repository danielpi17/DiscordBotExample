package com.dan.bot.commands.server.rolemanagement;

import com.dan.bot.ServerHandler;
import com.dan.bot.dataintegrators.RoleRelations;
import com.dan.bot.dataintegrators.Temporary;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class Role extends DiscordSlashCmd {
    public Role() {
        super("role", "Give a user a role",
                SlashUtil.newArg(SlashCommandOptionType.BOOLEAN, "type", "True to add, false to remove", true),
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to assign role to", true),
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "role", "Role to assign", true));
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        boolean type = i.getArgumentBooleanValueByName("type").get();
        User user = i.getArgumentUserValueByName("user").get();
        org.javacord.api.entity.permission.Role role = i.getArgumentRoleValueByName("role").get();
        System.out.println(RoleRelations.isUserAllowedToUse(i.getUser(), role));
        if(!RoleRelations.isUserAllowedToUse(i.getUser(), role)) {
            error("You do not have the permission to assign this role!");
            return;
        }

        Temporary.removeTempRole(user, role);

        if(type) {
            role.addUser(user, "Assigned by " + i.getUser().getIdAsString());
        } else {
            role.removeUser(user, "Removed by " + i.getUser().getIdAsString());
        }

        replyMemberOnly("",
                EmbedTools.success(i.getServer().get().getIdAsString())
                        .setDescription("Successfully " + (type ? "assigned" : "removed") + " the role " + role.getMentionTag() + " to " + user.getMentionTag())
        );
        EmbedBuilder embed = EmbedTools.info(i.getServer().get().getIdAsString())
                .setTitle("Role Logs")
                .addInlineField("Type", type + "")
                .addInlineField("Assigner", i.getUser().getMentionTag())
                .addInlineField("User", user.getMentionTag())
                .addInlineField("Role", role.getMentionTag());
        new ServerHandler(i.getServer().get().getIdAsString()).roles.equals(embed);
    }
}
