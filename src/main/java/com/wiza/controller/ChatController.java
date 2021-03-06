package com.wiza.controller;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.wiza.view.ChatView;
import com.wiza.view.data.ChatMessages;
import com.wiza.view.data.Chats;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.codahale.metrics.MetricRegistry.name;
import static java.util.stream.Collectors.toList;

@Path("/chat/{userOne}/{userTwo}")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
public class ChatController {

    private final Chats chats;
    private final Timer responses;

    public ChatController(MetricRegistry metrics, Chats chats) {
        responses = metrics.timer(name(ChatController.class, "responses"));
        this.chats = chats;
    }



    @Timed
    @GET
    public ChatView chatBetween(@PathParam("userOne") final Optional<String> userOne,
                                @PathParam("userTwo") final Optional<String> userTwo) {
        final Timer.Context context = responses.time();

        try {
            String userOneName = userOne.get();
            String userTwoName = userTwo.get();
            return new ChatView(userOneName, userTwoName, chats.chatBetween(userOneName, userTwoName),
                    chats.belongingTo(userOneName)
                            .stream()
                            .map(c -> new ChatMessages(userOneName, c))
                            .collect(toList()));
        } finally {
            context.stop();
        }

    }

    @Timed
    @POST
    public ChatView newMessage(
            @PathParam("userOne") Optional<String> from,
            @PathParam("userTwo") Optional<String> to,
            @FormParam("message") Optional<String> message
    ) {
        String formattedMessage = from.get() + ": " + message.get() + "";
        chats.addMessageToChat(from.get(), to.get(), formattedMessage);
        return chatBetween(from, to);

    }

}