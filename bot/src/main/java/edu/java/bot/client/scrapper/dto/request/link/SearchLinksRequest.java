package edu.java.bot.client.scrapper.dto.request.link;

public record SearchLinksRequest(
        long tgChatId,
        String type,
        Long offset,
        String cursor,
        Integer limit
) {
}
