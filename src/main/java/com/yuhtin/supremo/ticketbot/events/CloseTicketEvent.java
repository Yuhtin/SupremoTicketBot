package com.yuhtin.supremo.ticketbot.events;

import com.yuhtin.supremo.ticketbot.utils.TicketUtils;
import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class CloseTicketEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        val channel = event.getChannel();
        if (event.getUser().isBot()
                || !TicketUtils.isTicketChannel(channel)
                || !event.getReactionEmote().getAsReactionCode().equals("\uD83D\uDD14")) return;

        val author = event.getMember();
        if (!author.hasPermission(Permission.VOICE_MUTE_OTHERS)) {

            channel.sendMessage("\uD83D\uDCA5 OPS! Você não pode fechar o ticket, peça para um **Suporte** fechar.")
                    .queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            return;

        }

        val guild = channel.getGuild();
        val member = guild.getMemberById(channel.getTopic().split(" ")[1]);
        if (member != null) {
            channel.getManager().removePermissionOverride(member).queue();
            member.getUser()
                    .openPrivateChannel()
                    .queue(privateChannel -> privateChannel.sendMessage("❤️ O seu ticket foi **finalizado**, gostou do atendimento? " +
                                    "Avalie-nos em <#875100896290300025>. " +
                                    "Divulgue-se em <#872516425502306334>")
                            .queue());

            val role = guild.getRoleById(875093519071580160L);
            if (role != null) guild.addRoleToMember(member, role).queue();

        }

        event.getChannel().deleteMessageById(event.getMessageIdLong()).queue();

        channel.sendMessage("\uD83E\uDEA7 Você fechou o ticket com sucesso. O jogador ainda pode visualizar ele.").queue(message -> {
            message.reply(":ballot_box_with_check: Apagando ticket em **3 minutos**.")
                    .queue($ -> $.getTextChannel().delete().queueAfter(3, TimeUnit.MINUTES));
        });

    }
}
