package com.dan.bot.commands.server.staffing;

import com.dan.bot.Main;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.Duration;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.time.Instant;

public class Slowmode extends DiscordSlashCmd {
    public Slowmode() {
        super("slowmode", "Toggle chat slowmode", ServerPermissions.SLOWMODE,
                SlashUtil.newArg("duration", "Slowmode duration", true));
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        Instant duration = Duration.durationLeft(i.getArgumentStringValueByName("duration").get());

        if(duration == null) {
            error("Duration is invalid!");
            return;
        }

        if((int) (duration.getEpochSecond() - Instant.now().getEpochSecond()) > 3600*6) {
            error("Duration for slowmode must be less than 6 hours!");
            return;
        }

        i.getChannel().get().asServerTextChannel().get().updateSlowmodeDelayInSeconds((int) (duration.getEpochSecond() - Instant.now().getEpochSecond()));

        reply("", EmbedTools.success(i.getServer().get().getIdAsString())
                .setDescription("Successfully set slowmode to " + i.getArgumentStringValueByName("duration").get()));
    }
}