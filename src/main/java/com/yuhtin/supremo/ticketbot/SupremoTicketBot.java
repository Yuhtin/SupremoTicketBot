package com.yuhtin.supremo.ticketbot;

import com.yuhtin.supremo.ticketbot.command.CommandMap;
import com.yuhtin.supremo.ticketbot.command.impl.SendTicketMessageCommand;
import com.yuhtin.supremo.ticketbot.utils.Logger;
import lombok.val;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class SupremoTicketBot {

    public static void main(String[] args) {

        val logger = Logger.getLogger();
        logger.info("Starting systems");

        try {

            JDABuilder.createDefault("ODcyMTMwNzA4NDE4NzQ0Mzgw.YQlZBA.0fJ14a9NTKDdObDh7Q-LdCubqrg")
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();

            val commandMap = new CommandMap("$");
            commandMap.register("ticketmessage", new SendTicketMessageCommand(), "tm");

            Runtime.getRuntime().addShutdownHook(new Thread(SupremoTicketBot::onShutdown));

        } catch (LoginException exception) {

            logger.severe("Error when connect to bot, authToken is incorrect");
            logger.severe("System shutdown");

            System.exit(0);

        }

    }

    public static void onShutdown() {
        Logger.getLogger().info("Shutdown successfully");
    }

}
