package com.luizaprestes.frame.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.user.impl.SelfInfoImpl;
import com.luizaprestes.frame.event.impl.ReadyEvent;
import com.luizaprestes.frame.handler.SocketHandler;
import com.luizaprestes.frame.handler.EntityBuilder;
import com.luizaprestes.frame.utils.Logger;

import java.util.ArrayList;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public class ReadyHandler extends SocketHandler {

    private final Frame client;
    private final EntityBuilder builder;

    private final Logger logger = new Logger(ReadyHandler.class, true);

    private final ObjectMapper map;

    public ReadyHandler(Frame client, int responseNumber, EntityBuilder builder) {
        super(client, responseNumber);
        this.client = client;
        this.builder = builder;
        this.map = client.getMapper();
    }

    @Override
    public void handle(String context) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            client.setSelfInfo(builder.createSelfInfo(content.get("user").toPrettyString()));

            final ArrayNode guilds = (ArrayNode) content.get("guilds");
            final ArrayNode muted = (ArrayNode) content.get("muted_channels");
            final ArrayList<TextChannel> mutedChannels = new ArrayList<>();

            for (int i = 0; i < guilds.size(); i++) {
                final Guild guild = builder.createGuild(guilds.get(i).toPrettyString());
                final TextChannel channel = guild.getTextChannels().getChannelById(muted.get(i).textValue());

                if (channel != null) {
                    mutedChannels.add(channel);
                }
            }

            ((SelfInfoImpl) client.getSelfInfo()).setMutedChannels(mutedChannels);

            final ArrayNode privateChannels = (ArrayNode) content.get("private_channels");

            for (int i = 0; i < privateChannels.size(); i++) {
                builder.createPrivateChannel(privateChannels.get(i).toPrettyString());
            }

            client.getEventClient().handle(new ReadyEvent(client, responseNumber));
            logger.debug("ReadyHandler was loaded.");

        } catch (Exception exception) {
            logger.error("A error occurred while reading payload from Ready WebSocket message.");
            exception.printStackTrace();
        }

    }

}