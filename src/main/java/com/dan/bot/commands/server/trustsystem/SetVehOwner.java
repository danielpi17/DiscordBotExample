package com.dan.bot.commands.server.trustsystem;

import com.dan.bot.NetworkHandler;
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

public class SetVehOwner extends DiscordSlashCmd {
    public SetVehOwner() {
        super("setvehowner", "Set vehicle owner trust", ServerPermissions.TRUST_ADMIN,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User", true), SlashUtil.newArg("vehiclename", "Vehicle name", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        try {
            User user = i.getArguments().get(0).getUserValue().get();
            String vehicle = i.getArguments().get(1).getStringValue().get();

            ResultSet rs = DB.query("SELECT * FROM vehtrust WHERE spawncode = ?", vehicle);
            boolean isallowed = false;
            while (rs.next()) {
                isallowed = true;
            }
            if(isallowed) {
                replyMemberOnly("", EmbedTools.info("NETWORK").setDescription("This vehicle is already owned!"));
            } else {
                NetworkHandler.trustSystem.sendMessage(EmbedTools.info("NETWORK")
                        .setDescription(i.getUser().getMentionTag() + " set personal owner to " + user.getMentionTag() + " on " + vehicle)
                );
                DB.execute("INSERT INTO `vehtrust` (`spawncode`, `discordid`) VALUES (?, ?)", vehicle, "discord:" + user.getIdAsString());
                reply("", EmbedTools.info("NETWORK")
                        .setDescription("Set vehicle owner to " + user.getMentionTag() + " on vehicle " + vehicle)
                );
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
