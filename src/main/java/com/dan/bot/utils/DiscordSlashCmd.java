package com.dan.bot.utils;

import com.dan.bot.Main;
import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class DiscordSlashCmd {
    private final String name;
    private final String desc;
    private ServerPermissions permissionLevel = ServerPermissions.NA;
    private NetworkPermissions networkPermissionLevel = NetworkPermissions.NA;
    private long server = 0;
    private final SlashCommandOption[] options;
    private SlashCommandCreateEvent e;
    private boolean networkCommand;

    public DiscordSlashCmd(String name, String desc, SlashCommandOption... options) {
        this.name = name;
        this.options = options;
        this.desc = desc;
    }
    public DiscordSlashCmd(String name, String desc, long server, SlashCommandOption... options) {
        this.name = name;
        this.options = options;
        this.desc = desc;
        this.server = server;
    }

    public DiscordSlashCmd(String name, String desc, ServerPermissions permissionLevel, SlashCommandOption... options) {
        this.name = name;
        this.options = options;
        this.desc = desc;
        this.permissionLevel = permissionLevel;
    }
    public DiscordSlashCmd(String name, String desc, NetworkPermissions permissionLevel, SlashCommandOption... options) {
        this.name = name;
        this.options = options;
        this.desc = desc;
        this.networkPermissionLevel = permissionLevel;
    }
    public DiscordSlashCmd(String name, String desc, ServerPermissions permissionLevel, long server, SlashCommandOption... options) {
        this.name = name;
        this.options = options;
        this.desc = desc;
        this.server = server;
        this.permissionLevel = permissionLevel;
    }
    public DiscordSlashCmd(String name, String desc, NetworkPermissions permissionLevel, long server, SlashCommandOption... options) {
        this.name = name;
        this.options = options;
        this.desc = desc;
        this.server = server;
        this.networkPermissionLevel = permissionLevel;
    }
    public static HashMap<String, DiscordSlashCmd> hash = new HashMap<>();
    public static HashMap<SlashCommandBuilder, Long> post = new HashMap<>();

    public void run(SlashCommandCreateEvent event) {
        this.e = event;

        this.networkCommand = networkPermissionLevel.i > permissionLevel.i;

        if(event.getSlashCommandInteraction().getCommandName().equals(name)) {
            System.out.println((!Permissions.isUserAllowed(event.getSlashCommandInteraction().getServer().get().getIdAsString(), event.getSlashCommandInteraction().getUser(), ServerPermissions.ADMINISTRATOR)));
            if(!event.getSlashCommandInteraction().getServer().isPresent()) {
                replyMemberOnly("", EmbedTools.error(networkCommand ? "NETWORK" : event.getSlashCommandInteraction().getServer().get().getIdAsString()).setDescription("This command may only be ran in a server!"));
                return;
            }
            if(!networkCommand ? (!Permissions.isUserAllowed(event.getSlashCommandInteraction().getServer().get().getIdAsString(), event.getSlashCommandInteraction().getUser(), ServerPermissions.ADMINISTRATOR)) : (!Permissions.isUserAllowed(event.getInteraction().getUser(), NetworkPermissions.ADMINISTRATOR))) {
                if(!networkCommand ? !Permissions.isUserAllowed(event.getSlashCommandInteraction().getServer().get().getIdAsString(), event.getSlashCommandInteraction().getUser(), permissionLevel) : Permissions.isUserAllowed(event.getInteraction().getUser(), networkPermissionLevel)) {
                    replyMemberOnly("", EmbedTools.error(networkCommand ? "NETWORK" : event.getSlashCommandInteraction().getServer().get().getIdAsString()).setDescription("This command requires the permission " + (networkCommand ? networkPermissionLevel : permissionLevel) + " to execute!"));
                    return;
                }
            }
            execute(event, event.getSlashCommandInteraction());
        }
    }

    public void register() {
        hash.put(name, this);
        SlashCommandBuilder cmd = SlashCommand.with(name, desc);
        List<SlashCommandOption> opti = new ArrayList<>(Arrays.asList(options));
        cmd.setOptions(opti);
        post.put(cmd, server);
    }

    public static void registerAll() {
        Main.api.addSlashCommandCreateListener(event -> {
            new Thread(() -> {
                if(hash.containsKey(event.getSlashCommandInteraction().getCommandName())) {
                    hash.get(event.getSlashCommandInteraction().getCommandName()).run(event);
                }
            }).start();
        });
    }
    public void reply(String reply, EmbedBuilder... embeds) {
        e.getSlashCommandInteraction().createImmediateResponder()
                .setContent(reply)
                .addEmbeds(embeds)
                .respond();
    }
    public void replyMemberOnly(String reply, EmbedBuilder... embeds) {
        e.getSlashCommandInteraction().createImmediateResponder()
                .setContent(reply)
                .addEmbeds(embeds)
                .setFlags(MessageFlag.EPHEMERAL)
                .respond();
    }

    public void error(String error) {
        replyMemberOnly("", EmbedTools.error(networkCommand ? "NETWORK" : e.getSlashCommandInteraction().getServer().get().getIdAsString()).setDescription(error));
    }

    public String name() {
        return name;
    }

    public abstract void execute(SlashCommandCreateEvent e, SlashCommandInteraction i);
}
