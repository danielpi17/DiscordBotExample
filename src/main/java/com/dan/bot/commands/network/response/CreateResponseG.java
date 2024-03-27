package com.dan.bot.commands.network.response;

import com.dan.bot.dataintegrators.Responses;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class CreateResponseG extends DiscordSlashCmd {
    public CreateResponseG() {
        super("create-response-g", "Create network response", NetworkPermissions.NETWORK_RESPONSE,
                SlashUtil.newArg("command", "Command", true),
                SlashUtil.newArg("json", "Json for embed", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        String command = i.getArgumentStringValueByName("command").get();
        String json = i.getArgumentStringValueByName("json").get();

        Responses.registerResponse("NETWORK", command, json);

        replyMemberOnly("", EmbedTools.success("NETWORK").setDescription("Successfully created !" + command));
    }
}
