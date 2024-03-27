package com.dan.bot.commands.server.rolemanagement;

import com.dan.bot.ServerHandler;
import com.dan.bot.dataintegrators.RoleRelations;
import com.dan.bot.dataintegrators.Temporary;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.Duration;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class Temprole extends DiscordSlashCmd {
    public Temprole() {
        super("temprole", "Give a user a temporary role",
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to assign role to", true),
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "role", "Role to assign", true),
                SlashUtil.newArg("duration", "Duration", true));
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        Role role = i.getArgumentRoleValueByName("role").get();
        String duration = i.getArgumentStringValueByName("duration").get();
        if(!RoleRelations.isUserAllowedToUse(i.getUser(), role)) {
            error("You do not have the permission to assign this role!");
            return;
        }
        if(Duration.durationLeft(duration) == null) {
            error("Your duration is formatted improperly!");
            return;
        }

        Temporary.removeTempRole(user, role);
        Temporary.createRole(user, duration, role);

        role.addUser(user, "Assigned by " + i.getUser().getIdAsString());

        replyMemberOnly("",
                EmbedTools.success(i.getServer().get().getIdAsString())
                        .setDescription("Successfully assigned the role " + role.getMentionTag() + " to " + user.getMentionTag() + " for " + duration)
        );


        EmbedBuilder embed = EmbedTools.info(i.getServer().get().getIdAsString())
                .setTitle("Role Add Logs")
                .addInlineField("Assigner", i.getUser().getMentionTag())
                .addInlineField("User", user.getMentionTag())
                .addInlineField("Role", role.getMentionTag())
                .addInlineField("Duration", duration);
        new ServerHandler(i.getServer().get().getIdAsString()).roles.equals(embed);
    }
}
