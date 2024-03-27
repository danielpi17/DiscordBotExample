package com.dan.bot.commands.server.rolepermissions;

import com.dan.bot.dataintegrators.RoleRelations;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class SetRoleChild extends DiscordSlashCmd {
    public SetRoleChild() {
        super("set-role-child", "Set role child", ServerPermissions.ROLE_FAMILY_MANAGEMENT,
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "parent", "Parent", true),
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "child", "Child", true),
                SlashUtil.newArg(SlashCommandOptionType.BOOLEAN, "boolean", "Allow or reject", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        Role parent = i.getArgumentRoleValueByName("parent").get();
        Role child = i.getArgumentRoleValueByName("child").get();
        boolean bool = i.getArgumentBooleanValueByName("boolean").get();

        RoleRelations.setRoleRelation(parent, child, bool);

        replyMemberOnly("", EmbedTools.info(i.getServer().get().getIdAsString()).setDescription("Successfully set role relation of parent " + parent.getMentionTag() + " and child " + child.getMentionTag() + " to " + bool));
    }
}
