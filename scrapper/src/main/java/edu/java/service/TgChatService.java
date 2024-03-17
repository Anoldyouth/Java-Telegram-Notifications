package edu.java.service;

import edu.java.model.TgChat;
import edu.java.util.State;

public interface TgChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);

    TgChat updateState(long tgChatId, State state);
}