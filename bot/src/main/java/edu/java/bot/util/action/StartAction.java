package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.ResponseData;
import edu.java.bot.dto.response.StartResponse;
import edu.java.bot.util.CommandEnum;
import java.util.Optional;

public class StartAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.START.getCommand())) {
            return Optional.empty();
        }

        /*
         * TODO Сделать проверку на регистрацию пользователя
         */

        return Optional.of(new StartResponse(update).makeResponse());
    }
}
