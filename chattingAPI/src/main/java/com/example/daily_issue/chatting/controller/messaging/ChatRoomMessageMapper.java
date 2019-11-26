package com.example.daily_issue.chatting.controller.messaging;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-25
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-25)
 */

import com.example.daily_issue.chatting.config.MessageURIConsts;
import com.example.daily_issue.chatting.domain.message.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 */
@RestController
@MessageMapping(MessageURIConsts.TOPIC_CHAT_CONTEXT)
public class ChatRoomMessageMapper {

    @MessageMapping("/{roomId}/chat.sendMessage")
    @SendTo(MessageURIConsts.TOPIC + "/{roomId}/chat")
    public ChatMessage sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage)
    {
        return chatMessage;
    }

    @MessageMapping("/{roomId}/chat.addUser")
    @SendTo(MessageURIConsts.TOPIC + "/{roomId}/chat")
    public ChatMessage addUser(@DestinationVariable String roomId,
                               @Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @SubscribeMapping("/{roomId}/chat")
    public void subscribe(@DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor)
    {
        headerAccessor.getSessionAttributes().put("topicUri", headerAccessor.getDestination());
        System.out.println(headerAccessor);
        System.out.println("IN SUBSCRIBE");
    }
}
