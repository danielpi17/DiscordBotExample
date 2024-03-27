package com.dan.bot.commands.server.trustsystem;

import com.dan.bot.NetworkHandler;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.sql.ResultSet;

public class ClearVehOwner extends DiscordSlashCmd {
    public ClearVehOwner() {
        super("clearvehowner", "Set vehicle owner trust", ServerPermissions.TRUST_ADMIN,
                SlashUtil.newArg("vehiclename", "Vehicle name", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        try {
            String vehicle = i.getArguments().get(0).getStringValue().get();

            ResultSet rs = DB.query("SELECT * FROM vehtrust WHERE spawncode = ?", vehicle);
            boolean isallowed = false;
            while (rs.next()) {
                isallowed = true;
            }
            if(!isallowed) {
                replyMemberOnly("", EmbedTools.info("NETWORK").setDescription("This vehicle is not owned!"));
            } else {
                NetworkHandler.trustSystem.sendMessage(EmbedTools.info("NETWORK").setDescription(i.getUser().getMentionTag() + " cleared owner on " + vehicle));
                DB.execute("DELETE FROM vehtrust WHERE spawncode = ?", vehicle);
                replyMemberOnly("", EmbedTools.success("NETWORK").setDescription("Cleared vehicle owner on vehicle " + vehicle));
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
