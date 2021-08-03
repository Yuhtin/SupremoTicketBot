package com.yuhtin.supremo.ticketbot.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class TicketUtils {

    public static int getNextTicketNumber(Guild guild) {

        VoiceChannel channel = guild.getVoiceChannelById(872131438538002443L);
        if (channel == null) return 1;

        int ticket = Integer.parseInt(channel.getName().split(" ")[1]) + 1;
        channel.getManager()
                .setName("Tickets: " + ticket)
                .queue();

        return ticket;

    }

    public static boolean channelIsTicket(TextChannel channel) {
        return channel.getName().startsWith("ticket-") && channel.getTopic() != null;
    }

}
