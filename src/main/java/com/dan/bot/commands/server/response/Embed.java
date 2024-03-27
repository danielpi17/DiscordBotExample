package com.dan.bot.commands.server.response;

import com.dan.bot.dataintegrators.JSONFormatter;
import com.dan.bot.dataintegrators.Responses;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class Embed extends DiscordSlashCmd {
    public Embed() {
        super("embed", "Embed as bot", ServerPermissions.RESPONSE_COMMAND,
                SlashUtil.newArg("json", "Json for embed", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        String json = i.getArgumentStringValueByName("json").get();

        i.getChannel().get().sendMessage(JSONFormatter.formatJson(json));

        replyMemberOnly("Success");
    }
}
