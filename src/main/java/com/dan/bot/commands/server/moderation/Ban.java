package com.dan.bot.commands.server.moderation;

import com.dan.bot.dataintegrators.PunishmentIntegration;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.*;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;

public class Ban extends DiscordSlashCmd {
    public Ban() {
        super("ban", "Ban a user from the server", ServerPermissions.BAN,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to ban", true),
                SlashUtil.newArg("reason", "Reason for ban", true),
                SlashUtil.newArg("duration", "Duration of ban")
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        String reason = i.getArgumentStringValueByName("reason").get();
        Optional<String> duration = i.getArgumentStringValueByName("duration");
        if(duration.isPresent()) {
            if(Duration.durationLeft(duration.get()) == null) {
                error("Timestamp is formatted invalidly! (1d yields 1 day).");
                return;
            }
        }
        PunishmentIntegration.issuePunishment(
                user.getIdAsString(),
                i.getUser().getIdAsString(),
                PunishmentIntegration.Type.BAN,
                reason,
                duration.orElse("Permanent"),
                i.getServer().get().getIdAsString(),
                true
        );

        reply("", EmbedTools.success(i.getServer().get().getIdAsString())
                .setDescription("Successfully banned " + user.getMentionTag() + " for " + reason + " with duration " + duration.orElse("Permanent")));
    }
}
