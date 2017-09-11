package br.com.joaops.cliente.config;

import br.com.joaops.cliente.json.domain.PingJson;
import br.com.joaops.cliente.json.protocol.Message;
import br.com.joaops.cliente.strategy.Strategy;
import br.com.joaops.cliente.util.CONSTANTES;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

/**
 *
 * @author João Paulo
 */
@Configuration
@PropertySource({"classpath:configuracao.properties"})
@PropertySource(value = {"file:configuracao.properties"}, ignoreResourceNotFound = true)
public class StompSessionConfig {
    
    @Autowired
    private Environment env;
    
    @Autowired
    private BeanFactory beanFactory;
    
    /**
     * Realiza a Conexão com o Servidor e Retorna um Objeto Pra Fazer a Comuncação
     */
    @Bean
    public StompSession getStompSession() {
        try {
            StandardWebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
            List<Transport> transports = new ArrayList<>(1);
            transports.add(new WebSocketTransport(simpleWebSocketClient));
            SockJsClient sockJsClient = new SockJsClient(transports);
            sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
            WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
            WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
            String uri = env.getProperty("server.websocket.uri");
            String auth = env.getProperty("client.websocket.auth");
            headers.add("Authorization", "Basic " + auth);
            ListenableFuture<StompSession> future = stompClient.connect(uri, headers, new MyHandler());
            StompSession stompSession = future.get();
            subscribeTopicPing(stompSession);
            subscribeQueuePing(stompSession);
            subscribeQueueMessage(stompSession);
            return stompSession;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("ERRO: " + e);
            return null;
        }
    }
    
    private void subscribeTopicPing(StompSession stompSession) {
        stompSession.subscribe(CONSTANTES.TOPICS.PING, new StompFrameHandler() {
            @Override
            public java.lang.reflect.Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }
            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                try {
                    String json = new String((byte[]) o, StandardCharsets.UTF_8);
                    System.out.println("JSON: " + json);
                    ObjectMapper mapper = new ObjectMapper();
                    PingJson ping = mapper.readValue(json, PingJson.class);
                } catch (Exception e) {
                    System.err.println("ERRO " + e);
                }
            }
        });
    }
    
    private void subscribeQueuePing(StompSession stompSession) {
        stompSession.subscribe("/user" + CONSTANTES.QUEUES.PING, new StompFrameHandler() {
            @Override
            public java.lang.reflect.Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }
            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                try {
                    String json = new String((byte[]) o, StandardCharsets.UTF_8);
                    System.out.println("JSON: " + json);
                    ObjectMapper mapper = new ObjectMapper();
                    PingJson request = mapper.readValue(json, PingJson.class);
                } catch (Exception e) {
                    System.err.println("ERRO " + e);
                }
            }
        });
    }
    
    private void subscribeQueueMessage(StompSession stompSession) {
        stompSession.subscribe("/user" + CONSTANTES.QUEUES.MESSAGE, new StompFrameHandler() {
            @Override
            public java.lang.reflect.Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }
            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                try {
                    String json = new String((byte[]) o, StandardCharsets.UTF_8);
                    System.out.println("JSON: " + json);
                    ObjectMapper mapper = new ObjectMapper();
                    Message request = mapper.readValue(json, Message.class);
                    executarComando(request);
                } catch (Exception e) {
                    System.err.println("ERRO " + e);
                }
            }
        });
    }
    
    private void executarComando(Message message) {
        Strategy s = beanFactory.getBean(message.getOperacao(), Strategy.class);
        if (s != null) {
            s.executar(message);
        }
    }
    
}