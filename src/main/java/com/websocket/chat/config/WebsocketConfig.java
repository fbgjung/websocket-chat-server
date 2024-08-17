package com.websocket.chat.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
handler를 이용하여 WebSocket을 활성화하기 위한 config 파일
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSocket // WebSocket 활성화
public class WebsocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat") // WebSocket에 접속하기 위한 endpoint
                .setAllowedOrigins("*"); // 도메인이 다른 서버에서도 접속 가능하도록 CORS 설정 추가
    }
}
