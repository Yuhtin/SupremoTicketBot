package com.yuhtin.supremo.ticketbot.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum TicketTypes {

    BUYS("Compras", "🛒", 0L),
    PARTNER("Programa de Parceiros", "🤝", 0L),
    BUG_REPORT("Reportes de Bugs", "⚠", 0L),
    FINANCIAL("Financeiro", "💲", 0L),
    QUESTIONS("Dúvidas", "❓", 0L);

    private final String fancyName;
    private final String emoji;
    private final long categoryId;

}
