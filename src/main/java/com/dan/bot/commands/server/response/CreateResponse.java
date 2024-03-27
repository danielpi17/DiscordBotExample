package com.dan.bot.commands.server.response;

import com.dan.bot.dataintegrators.Responses;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class CreateResponse extends DiscordSlashCmd {
    public CreateResponse() {
        super("create-response", "Create response", ServerPermissions.ADMINISTRATOR,
                SlashUtil.newArg("command", "Command", true),
                SlashUtil.newArg("json", "Json for embed", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        String command = i.getArgumentStringValueByName("command").get();
        String json = i.getArgumentStringValueByName("json").get();

        Responses.registerResponse(i.getServer().get().getIdAsString(), command, json);

        replyMemberOnly("", EmbedTools.success(i.getServer().get().getIdAsString()).setDescription("Successfully created !" + command));
    }
}
