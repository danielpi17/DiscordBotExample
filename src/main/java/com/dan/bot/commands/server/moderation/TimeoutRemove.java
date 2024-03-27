package com.dan.bot.commands.server.moderation;

import com.dan.bot.dataintegrators.PunishmentIntegration;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class TimeoutRemove extends DiscordSlashCmd {
    public TimeoutRemove() {
        super("timeout-remove", "Remove a timeout from a user from the server", ServerPermissions.TIMEOUT_REMOVE,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to untimeout", true),
                SlashUtil.newArg("reason", "Reason for untimeout", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        String reason = i.getArgumentStringValueByName("reason").get();
        PunishmentIntegration.issuePunishment(
                user.getIdAsString(),
                i.getUser().getIdAsString(),
                PunishmentIntegration.Type.TIMEOUT_REMOVE,
                reason,
                "Permanent",
                i.getServer().get().getIdAsString(),
                true
        );

        reply("", EmbedTools.success(i.getServer().get().getIdAsString())
                .setDescription("Successfully timeout-removed " + user.getMentionTag() + " for " + reason));
    }
}
