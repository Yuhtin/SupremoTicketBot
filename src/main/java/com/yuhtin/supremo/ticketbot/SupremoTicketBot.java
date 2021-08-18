package com.yuhtin.supremo.ticketbot;

import com.yuhtin.supremo.ticketbot.command.CommandHandler;
import com.yuhtin.supremo.ticketbot.command.CommandMap;
import com.yuhtin.supremo.ticketbot.command.impl.SendTicketMessageCommand;
import com.yuhtin.supremo.ticketbot.events.BotReadyEvent;
import com.yuhtin.supremo.ticketbot.events.CloseTicketEvent;
import com.yuhtin.supremo.ticketbot.events.CreateTicketEvent;
import com.yuhtin.supremo.ticketbot.events.MemberEnterEvent;
import com.yuhtin.supremo.ticketbot.utils.Logger;
import lombok.val;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class SupremoTicketBot {

    public static void main(String[] args) {

        val logger = Logger.getLogger();
        logger.info("Starting systems");

        try {

            val jda = JDABuilder.createDefault("ODcyMTMwNzA4NDE4NzQ0Mzgw.YQlZBA.0fJ14a9NTKDdObDh7Q-LdCubqrg")
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .addEventListeners(new CreateTicketEvent(), new BotReadyEvent(), new CloseTicketEvent(), new MemberEnterEvent())
                    .enableIntents(Arrays.asList(GatewayIntent.values()))
                    .build();

            val commandMap = new CommandMap("$");
            commandMap.register("ticketmessage", new SendTicketMessageCommand(), "tm");

            val commandHandler = new CommandHandler(commandMap);
            jda.addEventListener(commandHandler);

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
