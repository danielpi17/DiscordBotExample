package com.dan.bot.commands.server.rolepermissions;

import com.dan.bot.dataintegrators.RoleRelations;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class SetUserChild extends DiscordSlashCmd {
    public SetUserChild() {
        super("set-user-child", "Set user child", ServerPermissions.ROLE_FAMILY_MANAGEMENT,
                SlashUtil.newArg(SlashCommandOptionType.USER, "parent", "Parent", true),
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "child", "Child", true),
                SlashUtil.newArg(SlashCommandOptionType.BOOLEAN, "boolean", "Allow or reject", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User parent = i.getArgumentUserValueByName("parent").get();
        Role child = i.getArgumentRoleValueByName("child").get();
        boolean bool = i.getArgumentBooleanValueByName("boolean").get();

        RoleRelations.setRoleRelation(parent, child, bool);

        replyMemberOnly("", EmbedTools.info(i.getServer().get().getIdAsString()).setDescription("Successfully set role relation of parent " + parent.getMentionTag() + " and child " + child.getMentionTag() + " to " + bool));
    }
}
