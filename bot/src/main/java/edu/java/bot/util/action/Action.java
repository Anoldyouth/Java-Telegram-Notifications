package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.ResponseData;

public interface Action extends ChainElement<Action> {
    ResponseData apply(Update update);
}
