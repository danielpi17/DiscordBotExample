package com.dan.bot.commands.server.trustsystem;

import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.security.Permission;
import java.sql.ResultSet;

public class TrustView extends DiscordSlashCmd {
    public TrustView() {
        super("trustview", "View trust", SlashUtil.newArg("vehicle", "Vehicle spawncode", true));
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        try {
            String vehicle = i.getArguments().get(0).getStringValue().get();
            ResultSet rs = DB.query("SELECT * FROM vehtrust WHERE spawncode = ?", vehicle);
            EmbedBuilder embed = EmbedTools.info("NETWORK");
            if (!rs.next()) {
                error("Vehicle does not exist!");
                return;
            }
            if(!rs.getString("discordid").replace("discord:", "").equals(i.getUser().getIdAsString())) {
                if(!Permissions.isUserAllowed(i.getServer().get().getIdAsString(), i.getUser(), ServerPermissions.TRUST_ADMIN)) {
                    error("You do not own this vehicle!");
                    return;
                }
            }
            ResultSet re = DB.query("SELECT * FROM vehtrustuser WHERE spawncode = ?", vehicle);
            String str = "";
            while (re.next()) {
                str = str + "<@" + re.getString("discordid") + "> | ";
            }
            str = str + "asadsdrwdsjkadjfnpwqenpxxccmslkamdmsf2412242";
            str = str.replace(" | asadsdrwdsjkadjfnpwqenpxxccmslkamdmsf2412242", "");
            str = str.replace("asadsdrwdsjkadjfnpwqenpxxccmslkamdmsf2412242", "");
            embed.setDescription("Access to " + vehicle + ": " + str);
            replyMemberOnly("", embed);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
