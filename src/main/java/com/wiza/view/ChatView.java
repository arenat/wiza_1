package com.wiza.view;

import com.wiza.representation.Chat;
import com.wiza.view.data.ChatMessages;
import io.dropwizard.views.View;

import java.util.List;

public class ChatView extends View {

    private final List<ChatMessages> allChatsForThisUser;
    private final String currentUser;
    private final Chat currentChat;
    private final String otherUser;

    public ChatView(String currentUser, String otherUser, Chat currentChat, List<ChatMessages> allChatsForThisUser) {
        super("chat.mustache");
        this.currentUser = currentUser;
        this.otherUser = otherUser;
        this.currentChat = currentChat;
        this.allChatsForThisUser = allChatsForThisUser;
    }
    public List<ChatMessages> getChats(){
        return allChatsForThisUser;
    }

    public Chat getCurrentChat() {
        return currentChat;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getOtherUser() {
        return otherUser;
    }

}
