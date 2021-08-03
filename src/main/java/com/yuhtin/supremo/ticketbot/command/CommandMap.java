package com.yuhtin.supremo.ticketbot.command;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@Data
public final class CommandMap {

    private final String prefix;

    private Map<String, Command> commands = new HashMap<>();

    public void register(String key, Command value, String... aliases) {
        if (!key.startsWith(prefix)) key = prefix + key;

        commands.put(key, value);

        for (String alias : aliases) {

            if (!key.startsWith(prefix)) alias = prefix + alias;
            commands.put(alias, value);

        }

    }

}