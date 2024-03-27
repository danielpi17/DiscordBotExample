package com.dan.bot.commands.server.trustsystem;

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

public class Trusts extends DiscordSlashCmd {
    public Trusts() {
        super("trusts", "View your trusts", SlashUtil.newArg(SlashCommandOptionType.USER, "user", "[PERMISSION L6] Someone else"));
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getUser();
        if(i.getArguments().size() == 1) {
            if(!Permissions.isUserAllowed(i.getServer().get().getIdAsString(), i.getUser(), ServerPermissions.TRUST_ADMIN)) {
                user = i.getArguments().get(0).getUserValue().get();
            }
        }
        try {
            ResultSet rs = DB.query("SELECT * FROM vehtrust WHERE discordid = ?", "discord:" + user.getIdAsString());
            EmbedBuilder embed = EmbedTools.info("NETWORK");
            while (rs.next()) {
                String str = "";
                ResultSet re = DB.query("SELECT * FROM vehtrustuser WHERE spawncode = ?", rs.getString("spawncode"));
                while (re.next()) {
                    str = str + " <@" + re.getString("discordid").replace("discord:", "") + "> | ";
                }
                str = str + "asadsdrwdsjkadjfnpwqenpxxccmslkamdmsf2412242";
                str = str.replace(" | asadsdrwdsjkadjfnpwqenpxxccmslkamdmsf2412242", "");
                str = str.replace("asadsdrwdsjkadjfnpwqenpxxccmslkamdmsf2412242", "");
                embed.addField(rs.getString("spawncode"), str);
            }
            replyMemberOnly("", embed);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
