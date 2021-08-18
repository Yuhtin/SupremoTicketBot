package com.yuhtin.supremo.ticketbot.command.impl;

import com.yuhtin.supremo.ticketbot.command.Command;
import com.yuhtin.supremo.ticketbot.models.enums.TicketTypes;
import com.yuhtin.supremo.ticketbot.utils.DiscordUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.util.concurrent.TimeUnit;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class SendTicketMessageCommand implements Command {

    @Override
    public void execute(Message message, String args) {

        if (!DiscordUtils.hasPermission(message.getMember(), message.getChannel(), Permission.MANAGE_CHANNEL, false)) return;

        if (!message.getChannel().getName().contains("tickets")) {

            message.reply("❌ O nome do canal precisa ser `#tickets`").queue();
            return;

        }

        message.getChannel().sendMessage(args).queue(textMessage -> {
            for (TicketTypes value : TicketTypes.values()) {
                textMessage.addReaction(value.emoji()).queueAfter(3, TimeUnit.SECONDS);
            }
        });

        message.delete().queue();

    }
}
