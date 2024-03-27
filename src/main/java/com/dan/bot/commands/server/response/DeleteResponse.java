package com.dan.bot.commands.server.response;

import com.dan.bot.dataintegrators.Responses;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class DeleteResponse extends DiscordSlashCmd {
    public DeleteResponse() {
        super("delete-response", "Delete response", ServerPermissions.ADMINISTRATOR,
                SlashUtil.newArg("command", "Command", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        String command = i.getArgumentStringValueByName("command").get();

        Responses.deleteResponse(i.getServer().get().getIdAsString(), command);

        replyMemberOnly("", EmbedTools.success(i.getServer().get().getIdAsString()).setDescription("Successfully deleted !" + command));
    }
}
