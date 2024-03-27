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

public class Owner extends DiscordSlashCmd {
    public Owner() {
        super("owner", "view owner", ServerPermissions.TRUST_ADMIN, SlashUtil.newArg("spawncode", "[PERMISSION L6] Spawncode"));
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        String spawncode = i.getArguments().get(0).getStringValue().get();
        try {
            ResultSet rs = DB.query("SELECT * FROM vehtrust WHERE spawncode = ?", spawncode);
            if(rs.next()) {
                EmbedBuilder embed = EmbedTools.info("NETWORK");
                embed.addField("Owner", "<@" + rs.getString("discordid").replace("discord:", "") + ">");
                replyMemberOnly("", embed);
            } else {
                error("Not owned!");
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
