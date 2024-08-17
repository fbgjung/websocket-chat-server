package com.websocket.chat.dto;

import com.websocket.chat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>(); // 채팅방에 입장한 클라이언트들의 정보를 갖기위한 필드

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    // ENTER, TALK 분기 처리
    // ENTER: 채팅룸의 session 정보에 클라이언트의 session 리스트 추가해 놓았다가
    // 채팅룸에 메시지가 도착할 경우 채팅룸의 모든 session에 메시지를 발송
    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
