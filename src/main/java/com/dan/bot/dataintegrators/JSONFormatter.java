package com.dan.bot.dataintegrators;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

public class JSONFormatter {
    public static EmbedBuilder formatJson(String json) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        try {
            System.out.println(json);
            JSONObject jsonEmbed = new JSONObject(json);

            if (jsonEmbed.has("embeds")) {
                JSONArray array = jsonEmbed.getJSONArray("embeds");
                jsonEmbed = array.getJSONObject(0);
            }
            if (jsonEmbed.has("title")) {
                embedBuilder.setTitle(jsonEmbed.getString("title"));
            }

            if (jsonEmbed.has("description")) {
                embedBuilder.setDescription(jsonEmbed.getString("description"));
            }

            if (jsonEmbed.has("color")) {
                int red = (jsonEmbed.getInt("color") >> 16) & 0xFF;
                int green = (jsonEmbed.getInt("color") >> 8) & 0xFF;
                int blue = jsonEmbed.getInt("color") & 0xFF;

                Color color = new Color(red, green, blue);
                embedBuilder.setColor(color);
            }

            if (jsonEmbed.has("author")) {
                JSONObject jsonAuthor = jsonEmbed.getJSONObject("author");
                if (jsonAuthor.has("name")) {
                    embedBuilder.setAuthor(jsonAuthor.getString("name"));
                }
                if (jsonAuthor.has("url") && jsonAuthor.has("icon_url")) {
                    embedBuilder.setAuthor(jsonAuthor.getString("name"), jsonAuthor.getString("url"), jsonAuthor.getString("icon_url"));
                }
            }

            if (jsonEmbed.has("fields")) {
                for (int i = 0; i < jsonEmbed.getJSONArray("fields").length(); i++) {
                    JSONObject jsonField = (JSONObject) jsonEmbed.getJSONArray("fields").get(i);
                    if (jsonField.has("name") && jsonField.has("value")) {
                        embedBuilder.addField(jsonField.getString("name"), jsonField.getString("value"), jsonField.getBoolean("inline"));
                    }
                }
            }

            if (jsonEmbed.has("thumbnail")) {
                JSONObject jsonThumbnail = jsonEmbed.getJSONObject("thumbnail");
                if (jsonThumbnail.has("url")) {
                    embedBuilder.setThumbnail(jsonThumbnail.getString("url"));
                }
            }

            if (jsonEmbed.has("image")) {
                JSONObject jsonImage = jsonEmbed.getJSONObject("image");
                if (jsonImage.has("url")) {
                    embedBuilder.setImage(jsonImage.getString("url"));
                }
            }

            if (jsonEmbed.has("footer")) {
                JSONObject jsonFooter = jsonEmbed.getJSONObject("footer");
                if (jsonFooter.has("text")) {
                    embedBuilder.setFooter(jsonFooter.getString("text"));
                }
                if (jsonFooter.has("icon_url")) {
                    embedBuilder.setFooter(jsonFooter.getString("text"), jsonFooter.getString("icon_url"));
                }
            }
        } catch (Exception error) { }
        return embedBuilder;
    }
}
