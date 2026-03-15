/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.terra.ems.framework.websocket.server;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket服务器
 *
 * @author dengxueping
 */
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 持久化在线会话
     */
    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        SESSIONS.put(userId, session);
        log.info("[WebSocket] Connection established. userId: {}, sessionId: {}, total online: {}", userId,
                session.getId(), SESSIONS.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        SESSIONS.remove(userId);
        log.info("[WebSocket] Connection closed. userId: {}, total online: {}", userId, SESSIONS.size());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") String userId) {
        log.info("[WebSocket] Message from client. userId: {}, content: {}", userId, message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[WebSocket] Error occurred. sessionId: {}, message: {}", session.getId(), error.getMessage());
    }

    /**
     * 发送消息给指定用户
     */
    public static void sendToUser(String userId, String message) {
        Session session = SESSIONS.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("[WebSocket] Failed to send message to userId: {}, error: {}", userId, e.getMessage());
            }
        }
    }

    /**
     * 广播消息给所有用户
     */
    public static void broadcast(String message) {
        log.info("[WebSocket] Broadcasting message to {} users: {}", SESSIONS.size(), message);
        SESSIONS.forEach((userId, session) -> {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error("[WebSocket] Failed to broadcast message to userId: {}, error: {}", userId,
                            e.getMessage());
                }
            }
        });
    }
}
