package com.dan.bot.commands.network.moderation;

import com.dan.bot.ServerHandler;
import com.dan.bot.dataintegrators.PunishmentIntegration;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class NetworkNick extends DiscordSlashCmd {
    public NetworkNick() {
        super("network-nick", "Nick a user in the network", NetworkPermissions.NETWORK_NICK,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to nick", true),
                SlashUtil.newArg("nick", "Nickname", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        String nick = i.getArgumentStringValueByName("nick").get();

        for(Server server : user.getMutualServers()) {
            server.updateNickname(user, nick, "Network-nicked");
        }

        reply("", EmbedTools.success("NETWORK")
                .setDescription("Successfully network nicked " + user.getMentionTag() + " to " + nick));
    }
}
