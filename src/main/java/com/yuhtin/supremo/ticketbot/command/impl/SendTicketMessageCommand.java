package com.yuhtin.supremo.ticketbot.command.impl;

import com.yuhtin.supremo.ticketbot.command.Command;
import com.yuhtin.supremo.ticketbot.utils.DiscordUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class SendTicketMessageCommand implements Command {

    @Override
    public void execute(Message message, String args) {

        if (!DiscordUtils.hasPermission(message.getMember(), message.getChannel(), Permission.MANAGE_CHANNEL, true)) return;

        message.getChannel().sendMessage(args).queue();
        message.delete().queue();

    }
}
