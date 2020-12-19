package com.luizaprestes.wrapper.entities.guild.impl;

import com.luizaprestes.wrapper.entities.guild.Guild;

import java.util.Collection;
import java.util.HashMap;

public class GuildRegistry {

    private final HashMap<String, Guild> guilds;

    public GuildRegistry() {
        this.guilds = new HashMap<>();
    }

    public void registerGuild(final Guild guild) {
        guilds.put(guild.getId(), guild);
    }

    public Guild getGuildById(final String guildId) {
        return guilds.get(guildId);
    }

    public void cleanup(final String guildId) {
        guilds.remove(guildId);
    }

    public Collection<Guild> getAll() {
        return guilds.values();
    }
}
