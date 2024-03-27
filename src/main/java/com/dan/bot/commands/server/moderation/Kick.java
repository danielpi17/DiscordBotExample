package com.dan.bot.commands.server.moderation;

import com.dan.bot.dataintegrators.PunishmentIntegration;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.Duration;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;

public class Kick extends DiscordSlashCmd {
    public Kick() {
        super("kick", "Kick a user from the server", ServerPermissions.KICK,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to kick", true),
                SlashUtil.newArg("reason", "Reason for kick", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        String reason = i.getArgumentStringValueByName("reason").get();
        PunishmentIntegration.issuePunishment(
                user.getIdAsString(),
                i.getUser().getIdAsString(),
                PunishmentIntegration.Type.KICK,
                reason,
                "Permanent",
                i.getServer().get().getIdAsString(),
                true
        );

        reply("", EmbedTools.success(i.getServer().get().getIdAsString())
                .setDescription("Successfully kicked " + user.getMentionTag() + " for " + reason));
    }
}
