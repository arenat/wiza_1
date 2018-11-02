package com.wiza.view.data;

import com.wiza.representation.Chat;

import java.util.List;

public class ChatMessages {
    private final String userTwoName;
    private final List<String> messages;
    private final String userOneName;

    public ChatMessages(String userOneName, Chat c) {
        this.userOneName = userOneName;
        this.userTwoName = c.getOtherUser(userOneName);
        this.messages = c.getChat();
    }


    public String getUserTwo() {
        return userTwoName;
    }
    public List<String> getMessages() {
        return messages;
    }

    public String getUserOne() {
        return userOneName;
    }
}