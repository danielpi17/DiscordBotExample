package com.dan.bot;

import com.dan.bot.utils.AsyncLoops;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.RegisterImpl;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {
    public static DiscordApi api;
    public static void main(String[] args) {
        api = new DiscordApiBuilder().setToken(NetworkHandler.getToken()).setAllIntents().login().join();
        System.out.println(api.getOwnerId().get());

        NetworkHandler.registerNetwork();

        DB.updateConnection();
        DB.createTables();

        RegisterImpl.register();

        AsyncLoops.startLoops(NetworkHandler.tempRefresh);
    }
}
