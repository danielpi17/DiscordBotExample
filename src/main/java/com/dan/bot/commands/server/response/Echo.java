package com.dan.bot.commands.server.response;

import com.dan.bot.dataintegrators.JSONFormatter;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class Echo extends DiscordSlashCmd {
    public Echo() {
        super("echo", "Echo as bot", ServerPermissions.RESPONSE_COMMAND,
                SlashUtil.newArg("message", "Message", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        String message = i.getArgumentStringValueByName("message").get();

        i.getChannel().get().sendMessage(message);

        replyMemberOnly("Success");
    }
}
