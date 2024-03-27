package com.dan.bot.utils;

import com.dan.bot.Main;
import com.dan.bot.listeners.ResponseHandler;
import com.dan.bot.listeners.VoiceFix;
import com.dan.bot.listeners.loggable.Logging;
import com.dan.bot.listeners.loggable.PunishmentLogging;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegisterImpl {
    public static void register() {
        try {
            Main.api.addListener(new Logging());
            Main.api.addListener(new PunishmentLogging());
            Main.api.addListener(new VoiceFix());
            Main.api.addListener(new ResponseHandler());

            var reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(DiscordSlashCmd.class.getPackageName())));
            new Thread(() -> {
                for (Class<?> command : reflections.getSubTypesOf(DiscordSlashCmd.class)) {
                    try {
                        ((DiscordSlashCmd) command.newInstance()).register();
                        System.out.println(((DiscordSlashCmd) command.newInstance()).name() + " has been successfully registered as a command!");
                    } catch (Exception error) {
                        System.out.println(command.getName() + " has failed to register as a command!");
                    }
                }
                ArrayList<Long> servers = new ArrayList<>();
                for(Long l : DiscordSlashCmd.post.values()) {
                    if(!servers.contains(l)) {
                        servers.add(l);
                        Set<SlashCommandBuilder> bu = new HashSet<>();
                        for (SlashCommandBuilder b : DiscordSlashCmd.post.keySet()) {
                            if (DiscordSlashCmd.post.get(b).equals(l)) {
                                bu.add(b);
                            }
                        }
                        if(l == 0L) {
                            Main.api.bulkOverwriteGlobalApplicationCommands(bu).join();
                            System.out.println("Updated all global");
                        } else {
                            Main.api.bulkOverwriteServerApplicationCommands(l, bu).join();
                            System.out.println("Updated all " + l);
                        }
                    }
                }
                System.out.println("Finished registration");
            }).start();


            DiscordSlashCmd.registerAll();
        } catch (Exception ignored) { ignored.printStackTrace(); }
    }
}
