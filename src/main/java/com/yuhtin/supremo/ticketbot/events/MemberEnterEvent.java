package com.yuhtin.supremo.ticketbot.events;

import lombok.val;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class MemberEnterEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        val roleById = event.getGuild().getRoleById(875094646576013332L);
        if (roleById == null) return;

        event.getGuild().addRoleToMember(event.getMember(), roleById).queue();

    }
}
