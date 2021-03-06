package com.example.daily_issue.chatting.domain.message;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-25
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-25)
 */

import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 */
@Getter
@Setter
public class ChatMessage {
    private ChatMessageType type;
    private String content;
    private String sender;
}
