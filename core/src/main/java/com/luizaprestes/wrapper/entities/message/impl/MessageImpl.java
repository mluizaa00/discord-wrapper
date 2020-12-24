package com.luizaprestes.wrapper.entities.message.impl;

import com.luizaprestes.wrapper.entities.channel.TextChannel;
import com.luizaprestes.wrapper.entities.message.Message;
import com.luizaprestes.wrapper.entities.user.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MessageImpl implements Message {

    private String id;
    private User author;

    private String content;
    private TextChannel channel;

    private List<User> mentionedUsers;

    private boolean TTS;

    private Timestamp timeCreated;

    private Timestamp timeEdited = null;
    private boolean edited;

}
