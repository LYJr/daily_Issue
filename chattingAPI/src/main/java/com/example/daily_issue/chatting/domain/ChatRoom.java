package com.example.daily_issue.chatting.domain;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-25
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-25)
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 *
 */
@Getter
public class ChatRoom {

    private UUID id = UUID.randomUUID();

    @Setter
    private String name;

    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoom create(String name)
    {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        return chatRoom;
    }

}
