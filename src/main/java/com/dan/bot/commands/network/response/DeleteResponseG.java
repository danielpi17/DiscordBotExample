package com.dan.bot.commands.network.response;

import com.dan.bot.dataintegrators.Responses;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class DeleteResponseG extends DiscordSlashCmd {
    public DeleteResponseG() {
        super("delete-response-g", "Delete network response", NetworkPermissions.NETWORK_RESPONSE,
                SlashUtil.newArg("command", "Command", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        String command = i.getArgumentStringValueByName("command").get();

        Responses.deleteResponse("NETWORK", command);

        replyMemberOnly("", EmbedTools.success("NETWORK").setDescription("Successfully deleted !" + command));
    }
}
