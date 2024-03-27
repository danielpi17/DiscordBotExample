package com.dan.bot.commands.server.trustsystem;

import com.dan.bot.NetworkHandler;
import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.sql.ResultSet;

public class TrustVeh extends DiscordSlashCmd {
    public TrustVeh() {
        super("trustveh", "Trust Vehicle", SlashUtil.newArg("vehicle", "Vehicle spawncode", true), SlashUtil.newArg(SlashCommandOptionType.USER, "user", "user", true));
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        try {
            String vehicle = i.getArguments().get(0).getStringValue().get();
            User user  = i.getArguments().get(1).getUserValue().get();
            ResultSet rs = DB.query("SELECT * FROM vehtrust WHERE spawncode = '" + vehicle + "'");
            EmbedBuilder embed = EmbedTools.info("NETWORK");
            if (!rs.next()) {
                error("Vehicle does not exist!");
                return;
            }
            if (!rs.getString("discordid").replace("discord:", "").equals(i.getUser().getIdAsString())) {
                if(!Permissions.isUserAllowed(i.getServer().get().getIdAsString(), i.getUser(), ServerPermissions.TRUST_ADMIN)) {
                    error("You do not own this vehicle!");
                    return;
                }
            }
            ResultSet re = DB.query("SELECT * FROM vehtrustuser WHERE spawncode = '" + vehicle + "'");
            boolean alrhas = false;
            while(re.next()) {
                if(re.getString("discordid").replace("discord:", "").equals(user.getIdAsString())) {
                    alrhas = true;
                }
            }
            if(alrhas) {
                embed.setDescription("User is already trusted!");
            } else {
                NetworkHandler.trustSystem.sendMessage(EmbedTools.info("NETWORK")
                        .setDescription(i.getUser().getMentionTag() + " added access to " + user.getMentionTag() + " on " + vehicle)
                );
                DB.execute("INSERT INTO `vehtrustuser` (`spawncode`, `discordid`) VALUES (?, ?);", vehicle, "discord:" + user.getIdAsString());
                embed.setDescription("Trusted user " + user.getMentionTag() + " to vehicle " + vehicle);
            }
            replyMemberOnly("", embed);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
