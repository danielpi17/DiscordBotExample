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

import java.time.Instant;

public class Timeout extends DiscordSlashCmd {
    public Timeout() {
        super("timeout", "Timeout a user from the server", ServerPermissions.TIMEOUT,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to timeout", true),
                SlashUtil.newArg("reason", "Reason for timeout", true),
                SlashUtil.newArg("duration", "Duration of timeout", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        String reason = i.getArgumentStringValueByName("reason").get();
        String duration = i.getArgumentStringValueByName("duration").get();
        if(Duration.durationLeft(duration) == null) {
            error("Timestamp is formatted invalidly! (1d yields 1 day).");
            return;
        }
        if((Duration.durationLeft(duration).getEpochSecond() - Instant.now().getEpochSecond()) > 2419200) {
            error("Timestamp is formatted invalidly! (Maximum timeout is 28d).");
            return;
        }
        PunishmentIntegration.issuePunishment(
                user.getIdAsString(),
                i.getUser().getIdAsString(),
                PunishmentIntegration.Type.TIMEOUT,
                reason,
                duration,
                i.getServer().get().getIdAsString(),
                true
        );

        reply("", EmbedTools.success(i.getServer().get().getIdAsString())
                .setDescription("Successfully timed out " + user.getMentionTag() + " for " + reason + " with duration " + duration));
    }
}
