package com.dan.bot.utils;

import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.ArrayList;

public class SlashUtil {
    public static SlashCommandOption newArg(SlashCommandOptionType type, String name, String desc, boolean required) {
        return SlashCommandOption.create(type, name, desc, required);
    }
    public static SlashCommandOption newArg(SlashCommandOptionType type, String name, String desc) {
        return SlashCommandOption.create(type, name, desc);
    }
    public static SlashCommandOption newArg(String name, String desc, boolean required) {
        return SlashCommandOption.create(SlashCommandOptionType.STRING, name, desc, required);
    }
    public static SlashCommandOption newArg(String name, String desc) {
        return SlashCommandOption.create(SlashCommandOptionType.STRING, name, desc);
    }
    public static SlashCommandOption newArgWithChoices(String name, String desc, boolean required, String... options) {
        ArrayList<SlashCommandOptionChoice> choices = new ArrayList<>();
        for(String option : options) {
            choices.add(SlashCommandOptionChoice.create(option, option));
        }
        return SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, name, desc, required, choices);
    }
    public static SlashCommandOption newArgWithChoices(String name, String desc, String... options) {
        ArrayList<SlashCommandOptionChoice> choices = new ArrayList<>();
        for(String option : options) {
            choices.add(SlashCommandOptionChoice.create(option, option));
        }
        return SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, name, desc, false, choices);
    }

}
