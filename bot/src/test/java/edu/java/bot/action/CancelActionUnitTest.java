package edu.java.bot.action;

import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.CancelAction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CancelActionUnitTest {
    Action action;

    @BeforeEach
    void prepare() {
        action = new CancelAction();
    }

    @Test
    void correctCommand() {
        var update = ActionHelper.makeMockUpdateMessage(CommandEnum.CANCEL.getCommand());

        var response = action.apply(update);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text")).startsWith("Привет! Этот Бот"));
    }

    @Test
    void anotherMessage() {
        var update = ActionHelper.makeMockUpdateMessage("test");

        assertThrows(NullPointerException.class, () -> action.apply(update));
    }

    @Test
    void anotherUpdateContent() {
        var update = ActionHelper.makeMockUpdateEmpty();

        assertThrows(NullPointerException.class, () -> action.apply(update));
    }
}
