package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.Bot;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.dto.response.HelpResponse;
import edu.java.bot.dto.response.ListResponse;
import edu.java.bot.dto.response.StartResponse;
import edu.java.bot.dto.response.TrackResponse;
import edu.java.bot.dto.response.TrackUrlResponse;
import edu.java.bot.dto.response.UnknownCommandResponse;
import edu.java.bot.dto.response.UntrackResponse;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BotCommandsTest {
    Bot bot;
    TelegramBot telegramBotMock;

    @Captor
    ArgumentCaptor<BaseRequest<?, ?>> captor;

    @BeforeEach
    void startBot() {
        var telegramBot = mock(TelegramBot.class);
        telegramBotMock = telegramBot;
        bot = new Bot(telegramBot);

        bot.start();
    }

    static Arguments[] commands() {
        var update = makeMockUpdateMessage();

        return new Arguments[]{
                Arguments.of(
                        CommandEnum.START.getCommand(),
                        "Привет! Этот Бот",
                        new StartResponse(update).getCommands()
                ),
                Arguments.of(
                        CommandEnum.CANCEL.getCommand(),
                        "Привет! Этот Бот",
                        new StartResponse(update).getCommands()
                ),
                Arguments.of(
                        CommandEnum.HELP.getCommand(),
                        "*Список команд:*",
                        new HelpResponse(update).getCommands()
                ),
                Arguments.of(
                        CommandEnum.TRACK.getCommand(),
                        "Введите ссылку",
                        new TrackResponse(update).getCommands()
                ),
                Arguments.of(
                        CommandEnum.UNTRACK.getCommand(),
                        "Выберите, для какой ссылки прекратить отслеживание",
                        new UntrackResponse(update, List.of()).getCommands()
                ),
                Arguments.of(
                        CommandEnum.LIST.getCommand(),
                        "Список отслеживаемых страниц",
                        new ListResponse(update, List.of()).getCommands()
                ),
                Arguments.of(
                        "https://www.google.com",
                        "Ссылка https://www.google.com успешно добавлена для отслеживания.",
                        new TrackUrlResponse(update, "https://www.google.com").getCommands()
                ),
                Arguments.of(
                        "test",
                        "Неизвестная команда",
                        new UnknownCommandResponse(update).getCommands()
                )
        };
    }

    @ParameterizedTest
    @DisplayName("Тест команд")
    @MethodSource("commands")
    public void testCommands(String message, String responseMessageStart, SetMyCommands commands) {
        List<Update> updates = prepareMessageUpdate(message);

        int result = bot.process(updates);

        verify(telegramBotMock, times(2)).execute(captor.capture());
        List<BaseRequest<?, ?>> capturedArguments = captor.getAllValues();

        Assertions.assertTrue(
                ((String)capturedArguments.get(0).getParameters().get("text"))
                        .startsWith(responseMessageStart)
        );
        assertThat(capturedArguments.get(1).getParameters().get("commands"))
                .isEqualTo(commands.getParameters().get("commands"));

        assertThat(result).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
    }

    private List<Update> prepareMessageUpdate(String messageText) {
        var chatMock = mock(Chat.class);
        when(chatMock.id()).thenReturn(1L);

        var messageMock = mock(Message.class);
        when(messageMock.text()).thenReturn(messageText);
        when(messageMock.chat()).thenReturn(chatMock);

        var updateMock = mock(Update.class);
        when(updateMock.message()).thenReturn(messageMock);

        return List.of(updateMock);
    }

    public static Update makeMockUpdateMessage() {
        var chatMock = mock(Chat.class);
        when(chatMock.id()).thenReturn(1L);

        var messageMock = mock(Message.class);
        when(messageMock.chat()).thenReturn(chatMock);

        var updateMock = mock(Update.class);
        when(updateMock.message()).thenReturn(messageMock);

        return updateMock;
    }
}
