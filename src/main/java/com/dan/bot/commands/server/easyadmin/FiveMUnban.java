package com.dan.bot.commands.server.easyadmin;

import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class FiveMUnban extends DiscordSlashCmd {
    public FiveMUnban() {
        super("fivem-unban", "FiveM Unban", ServerPermissions.FIVEM_UNBAN,
                SlashUtil.newArg("id", "Ban ID", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        String id = i.getArgumentStringValueByName("id").get();

        DB.execute("INSERT INTO easylink (`type`, `id`) VALUES ('UNBAN', ?)", id);

        reply("", EmbedTools.success(i.getServer().get().getIdAsString()).setDescription("Successfully queued " + id + " for an unban in game!"));
    }
}
