package com.dan.bot.commands.network.moderation;

import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public class NetworkPunishments extends DiscordSlashCmd {
    public NetworkPunishments() {
        super("network-punishments", "Network Punishments", NetworkPermissions.NETWORK_PUNISHMENTS,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        String timezone = "UTC";
        ResultSet rs = DB.query("SELECT * FROM punishments WHERE server = 'NETWORK' AND user = ? ORDER BY timestamp ASC", user.getIdAsString());

        HashMap<String, Map.Entry<String, Map.Entry<String, Map.Entry<String, Map.Entry<String, Map.Entry<String, String>>>>>> hm = new HashMap<>();

            try {
            while (rs.next()) {
                hm.put(
                        rs.getString("id"),
                        Map.entry(
                                rs.getString("reason"),
                                Map.entry(rs.getString("executor"),
                                        Map.entry(rs.getString("type"),
                                                Map.entry(rs.getString("duration"), Map.entry(
                                                        rs.getString("timestamp"), rs.getString("evidence")
                                                        )
                                                )))));
            }
        } catch (Exception error) {
            error.printStackTrace();
        }

        StringBuilder b = new StringBuilder();

        HashSet<String> s = new HashSet<>();
            for(String punishid : hm.keySet()) {
            s.add((punishid));
        }

        EmbedBuilder embed = EmbedTools.info(i.getServer().get().getIdAsString()).setDescription(b.toString());


        List<EmbedBuilder> eb = new ArrayList<>();
        int ebe = 0;
        boolean rp = false;

            for(String punishid : s) {
            String reason = hm.get(punishid + "").getKey();
            String executor = hm.get(punishid + "").getValue().getKey();
            String type = hm.get(punishid + "").getValue().getValue().getKey();
            String duration = hm.get(punishid + "").getValue().getValue().getValue().getKey();
            String timestamp = hm.get(punishid + "").getValue().getValue().getValue().getValue().getKey();
            String evidence = hm.get(punishid + "").getValue().getValue().getValue().getValue().getValue();

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));

            String timestampPrint;

            try {
                duration = Duration.toMaxTime((Long.parseLong(duration)-Long.parseLong(timestamp))) + " <t:" + Long.parseLong(duration) + ":F> (<t:" + Long.parseLong(duration) + ":R>)";
            } catch (Exception ignored) {}

            if(timestamp.equals("0")) {
                timestampPrint = "";
            } else {
                timestampPrint = "<t:" + (Long.parseLong(timestamp)/1000) + ":F> (<t:" + (Long.parseLong(timestamp)/1000) + ":R>)";
            }

            embed.addField(" ",
                    ":pushpin: **ID:** " + punishid +
                            "\n:information_source: **Reason:** " + reason +
                            "\n:man_police_officer: **Staff:** " + executor +
                            "\n:link: **Type:** " + type +
                            "\n:alarm_clock: **Issued On:** " + timestampPrint +
                            "\n:timer: **Duration:** " + duration +
                            "\n:camera_with_flash: **Evidence:** [Link](" + evidence + ")", true
            );
            ebe++;
            if(ebe >= 15) {
                if(!rp) {
                    reply("", embed);
                    rp = true;
                } else {
                    i.getChannel().get().sendMessage(embed);
                }
                embed.removeAllFields();
                ebe = 0;
            }
        }

            if(ebe > 0) {
            i.getChannel().get().sendMessage(embed);
        }
    }
}
