package com.websocket.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK; // 입장, 채팅
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
