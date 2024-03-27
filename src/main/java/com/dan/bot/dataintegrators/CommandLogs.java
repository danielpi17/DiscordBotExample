package com.dan.bot.dataintegrators;

import com.dan.bot.utils.DB;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.time.Instant;

public class CommandLogs {
    public static void logCommand(SlashCommandInteraction i) {
        String command = i.getFullCommandName();
        for(SlashCommandInteractionOption o : i.getArguments()) {
            command += " " + o.getName() + ":" + o.getStringRepresentationValue();
        }
        DB.execute("INSERT INTO `command_logs` (`timestamp`, `user`, `command`) VALUES (?, ?, ?)", Instant.now().toEpochMilli(), i.getUser().getIdAsString(), command);
    }
}
