package com.dan.bot.commands.network.moderation;

import com.dan.bot.dataintegrators.PunishmentIntegration;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class NetworkKick extends DiscordSlashCmd {
    public NetworkKick() {
        super("network-kick", "Kick a user from the network", NetworkPermissions.NETWORK_KICK,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User to kick", true),
                SlashUtil.newArg("reason", "Reason for kick", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        String reason = i.getArgumentStringValueByName("reason").get();
        PunishmentIntegration.issuePunishment(
                user.getIdAsString(),
                i.getUser().getIdAsString(),
                PunishmentIntegration.Type.NETWORK_KICK,
                reason,
                "Permanent",
                "NETWORK",
                true
        );

        reply("", EmbedTools.success("NETWORK")
                .setDescription("Successfully network kicked " + user.getMentionTag() + " for " + reason));
    }
}
