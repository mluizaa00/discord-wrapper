package com.luizaprestes.wrapper.entities.channel;

import com.luizaprestes.wrapper.entities.guild.model.Role;

import java.util.List;

public interface TextChannel extends Channel {

    String getTopic();

    boolean isNsfw();

    List<Role> getRoles();

    int getPosition();

}
