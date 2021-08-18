package com.yuhtin.supremo.ticketbot.events;

import com.yuhtin.supremo.ticketbot.utils.Logger;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class BotReadyEvent extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        Logger.getLogger().info("Bot started successfully");
    }
}
