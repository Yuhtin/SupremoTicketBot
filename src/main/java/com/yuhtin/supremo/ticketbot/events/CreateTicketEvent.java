package com.yuhtin.supremo.ticketbot.events;

import com.yuhtin.supremo.ticketbot.models.enums.TicketTypes;
import com.yuhtin.supremo.ticketbot.utils.Logger;
import com.yuhtin.supremo.ticketbot.utils.TicketUtils;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class CreateTicketEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        if (event.getMember().getUser().isBot()) return;

        TextChannel channel = event.getChannel();
        if (!channel.getName().contains("tickets")) return;

        event.getReaction().removeReaction(event.getUser()).queue();
        event.getReactionEmote().getAsReactionCode();

        TicketTypes type = null;
        for (TicketTypes value : TicketTypes.values()) {
            if (!event.getReactionEmote().getAsReactionCode().equals(value.emoji())) continue;
            type = value;
        }

        if (type == null) return;

        Category category = event.getGuild().getCategoryById(type.categoryId());
        if (category == null) return;

        TextChannel ticketChannel = findChannelByTopic(
                category,
                textChannel -> textChannel.getTopic() != null && textChannel.getTopic().contains(event.getUserId())
        );

        PrivateChannel privateChannel = event.getUser().openPrivateChannel().complete();
        if (!event.getUser().hasPrivateChannel() || privateChannel == null) {

            event.getChannel()
                    .sendMessage("💭 Habilite as mensagens diretas a partir deste servidor.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;

        }

        if (ticketChannel != null) {

            String hasTicketMessage = "💌 "
                    + event.getUser().getAsMention()
                    + ", você já abriu um pedido de suporte "
                    + ticketChannel.getAsMention();

            privateChannel.sendMessage(hasTicketMessage).queue();
            return;

        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("Feche o ticket clicando no sino abaixo");
        builder.setAuthor("| " + type.fancyName(), null, event.getGuild().getIconUrl());
        builder.setFooter("| Pedido de suporte aberto às", event.getUser().getAvatarUrl());
        builder.setColor(Color.CYAN);
        builder.setTimestamp(Instant.now());

        TicketTypes finalType = type;

        val role = event.getGuild().getRoleById(872131049394688031L);
        if (role == null) return;

        category.createTextChannel("ticket-" + TicketUtils.getTicketNumber(channel.getGuild()))

                .setTopic("Ticket: " + event.getUser().getId())

                .addPermissionOverride(
                        event.getGuild().getPublicRole(),
                        Collections.emptyList(),
                        Arrays.asList(
                                Permission.MESSAGE_READ,
                                Permission.MESSAGE_WRITE
                        )
                )

                .addPermissionOverride(
                        event.getMember(),
                        Arrays.asList(
                                Permission.MESSAGE_READ,
                                Permission.MESSAGE_WRITE,
                                Permission.MESSAGE_EMBED_LINKS,
                                Permission.MESSAGE_ATTACH_FILES
                        ),
                        Collections.emptyList()
                )

                .addPermissionOverride(
                        role,
                        Arrays.asList(
                                Permission.MESSAGE_READ,
                                Permission.MESSAGE_WRITE,
                                Permission.MESSAGE_EMBED_LINKS,
                                Permission.MESSAGE_ATTACH_FILES
                        ),
                        Collections.emptyList()
                )

                .queue(textChannel -> {

                    Logger.getLogger().info("Player " + event.getUser().getAsTag() + " openned a ticket (#" + textChannel.getName() + " - " + finalType.fancyName() + ")");
                    textChannel.sendMessage(role.getAsMention()).queue();
                    textChannel.sendMessage(builder.build()).queue(message -> message.addReaction("\uD83D\uDD14").queue());

                    String sucessMessage = "\uD83D\uDDE8️ "
                            + event.getUser().getAsMention()
                            + " Acesse seu pedido de suporte "
                            + textChannel.getAsMention();

                    privateChannel.sendMessage(sucessMessage).queue();

                });

    }

    /**
     * Search channel by filter
     *
     * @param category to search channel
     * @param filter   specifications that the channel should have
     * @return found channel
     */
    private TextChannel findChannelByTopic(Category category, Predicate<TextChannel> filter) {

        return category.getTextChannels()
                .stream()
                .filter(filter)
                .findAny()
                .orElse(null);

    }

}
