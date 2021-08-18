package com.yuhtin.supremo.ticketbot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscordUtils {

    public static boolean hasPermission(Member member, MessageChannel channel, Permission permission, boolean showMessage) {

        if (!member.hasPermission(permission)) {

            if (!showMessage) return false;

            channel.sendMessage("❌ Sem permissão para fazer isto.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));

            return false;

        }

        return true;

    }

}
