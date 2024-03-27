package com.dan.bot.commands.server.staffing;

import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class Clear extends DiscordSlashCmd {
    public Clear() {
        super("clear", "Clear messages in chat", ServerPermissions.CLEAR,
                SlashUtil.newArg(SlashCommandOptionType.LONG, "amount", "Amount to clear", true));
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        MessageSet set = i.getChannel().get().getMessages(Math.toIntExact(i.getArgumentLongValueByName("amount").get())).join();

        if (set.size() > 2) i.getChannel().get().bulkDelete(set);
        else i.getChannel().get().deleteMessages(set);

        reply("", EmbedTools.success(i.getServer().get().getIdAsString())
                .setDescription("Successfully cleared " + set.size() + " messages"));
    }
}
