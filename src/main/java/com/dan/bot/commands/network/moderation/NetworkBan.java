package com.dan.bot.commands.network.moderation;

import com.dan.bot.dataintegrators.PunishmentIntegration;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class NetworkBan extends DiscordSlashCmd {
    public NetworkBan() {
        super("network-ban", "Ban a user from the network", NetworkPermissions.NETWORK_BAN_REMOVE,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to ban", true),
                SlashUtil.newArg("reason", "Reason for ban", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        String reason = i.getArgumentStringValueByName("reason").get();
        PunishmentIntegration.issuePunishment(
                user.getIdAsString(),
                i.getUser().getIdAsString(),
                PunishmentIntegration.Type.NETWORK_BAN,
                reason,
                "Permanent",
                "NETWORK",
                true
        );

        reply("", EmbedTools.success("NETWORK")
                .setDescription("Successfully network banned " + user.getMentionTag() + " for " + reason));
    }
}
