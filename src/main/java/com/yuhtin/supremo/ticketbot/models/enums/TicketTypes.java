package com.yuhtin.supremo.ticketbot.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum TicketTypes {

    BUYS("Compras", "🛒", 872133105643814933L),
    PARTNER("Programa de Parceiros", "🤝", 872133198300205077L),
    BUG_REPORT("Reportes de Bugs", "⚠", 872133142658547763L),
    FINANCIAL("Financeiro", "💲", 872133256752005130L),
    QUESTIONS("Dúvidas", "❓", 872133318299246642L);

    private final String fancyName;
    private final String emoji;
    private final long categoryId;

}
