package jhkim105.tutorials.websocket;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
// https://docs.spring.io/spring-framework/reference/web/websocket/stomp/client.html
class WebSocketTests {

	@TestConfiguration
	static class WebsocketTestConfig {
		@Bean
		public WebSocketStompClient webSocketStompClient(ObjectMapper mapper) {
			var webSocketStompClient =  new WebSocketStompClient(webSocketClient());
			webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter(mapper));
			return webSocketStompClient;
		}

		private WebSocketClient webSocketClient() {
			return new StandardWebSocketClient();
		}
	}

	@Autowired
	private WebSocketStompClient stompClient;

	@LocalServerPort
	private int port;


	@Test
	void test() throws Exception {
		var url = String.format("ws://localhost:%d/ws", port);
		var sessionHandler = new MyStompSessionHandler();
		var stompSession = stompClient.connectAsync(url, sessionHandler).get(2, TimeUnit.SECONDS);
		assertThat(stompSession.isConnected()).isEqualTo(true);
	}

	static class MyStompSessionHandler extends StompSessionHandlerAdapter {
		@Override
		public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
			log.debug("sessionId: {}", session.getSessionId());

			// subscribe
			session.subscribe("/topic/something", new StompFrameHandler() {

				@Override
				public Type getPayloadType(StompHeaders headers) {
					return String.class;
				}

				@Override
				public void handleFrame(StompHeaders headers, Object payload) {
					log.debug("payload: {}", payload);
				}

			});

			// send
			session.send("/topic/something", "payload");
		}

		@Override
		public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
			log.debug("exception: {}", exception.toString());
		}
	}
}
