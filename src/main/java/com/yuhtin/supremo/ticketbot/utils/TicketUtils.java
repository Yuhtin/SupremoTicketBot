package com.yuhtin.supremo.ticketbot.utils;

import lombok.val;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class TicketUtils {

    public static int getTicketNumber(Guild guild) {

        val channel = guild.getVoiceChannelById(872131438538002443L);
        if (channel == null) return 1;

        val ticket = Integer.parseInt(channel.getName().split(" ")[1]) + 1;
        channel.getManager()
                .setName("Tickets: " + ticket)
                .queue();

        return ticket;

    }

    public static boolean isTicketChannel(TextChannel channel) {
        return channel.getName().startsWith("ticket-") && channel.getTopic() != null;
    }

}
