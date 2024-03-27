package com.dan.bot.listeners;

import com.dan.bot.dataintegrators.JSONFormatter;
import com.dan.bot.dataintegrators.Responses;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class ResponseHandler implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessage().getContent().startsWith("!")) {
            if(event.getServer().isPresent()) {
                event.getMessage().getChannel().sendMessage(JSONFormatter.formatJson(Responses.getResponse(event.getServer().get().getIdAsString(), event.getMessage().getContent().split(" ")[0].replaceFirst("!", ""))));
                event.getMessage().getChannel().sendMessage(JSONFormatter.formatJson(Responses.getResponse("NETWORK", event.getMessage().getContent().split(" ")[0].replaceFirst("!", ""))));
            }
        }
    }
}
