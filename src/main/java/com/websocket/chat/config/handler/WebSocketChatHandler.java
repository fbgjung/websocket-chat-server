package com.websocket.chat.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.dto.ChatMessage;
import com.websocket.chat.dto.ChatRoom;
import com.websocket.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/*
socket 통신은 서버와 클라이언트가 1:N으로 관계를 맺는다.
따라서 한 서버에 여러 클라이언트가 접속할 수 있으며,
서버에는 여러 클라이언트가 발송한 메시지를 받아 처리해줄 Handler가 필요하다.
client로부터 받은 메시지를 출력하고 client로 메시지를 보내는 역할을 한다.
session: 클라이언트와 서버 간의 WebSocket 연결을 나타낸다. 이 객체를 통해 메시지를 송수신한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload); // client 가 websocket을 통해 서버로 전송한 메시지 내용

//        TextMessage textMessage = new TextMessage("welcome chatting server"); // server가 응답한 메시지
//        session.sendMessage(textMessage); // WebSocket 세션을 통해 클라이언트에게 메시지를 전송

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }

}
