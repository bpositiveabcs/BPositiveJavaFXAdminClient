package bpos.adminclient.RestCommunication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CopyOnWriteArrayList;
public class ClientWebSocket {
    private static ClientWebSocket instance;
    private StompSession session;
    private final CopyOnWriteArrayList<WebSocketMessageListener> listeners = new CopyOnWriteArrayList<>();
    private boolean connected = false;

    private ClientWebSocket() {
    }
}
