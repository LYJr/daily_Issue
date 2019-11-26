package com.example.daily_issue.chatting.dao.repository;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-26
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-26)
 */

import com.example.daily_issue.chatting.domain.ChatRoom;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 *
 */
@Repository
public class ChattingRepository {

    @Getter
    private Map<UUID, ChatRoom> chatRoomMap;
    @Getter
    private Collection<ChatRoom> chatRooms;

    @PostConstruct
    public void roomInit()
    {
        chatRoomMap = Collections.unmodifiableMap(
                Stream.of(ChatRoom.create("1번방"), ChatRoom.create("2번방"), ChatRoom.create("3번방"))
                        .collect(Collectors.toMap(ChatRoom::getId, Function.identity())));
        chatRooms = Collections.unmodifiableCollection(chatRoomMap.values());
    }
}
