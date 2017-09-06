package br.com.joaops.cliente.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

/**
 *
 * @author João Paulo
 */
public class MyHandler extends StompSessionHandlerAdapter {
    
    private static final Logger LOGGER = LogManager.getLogger(MyHandler.class);
    
    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        LOGGER.info("Conectado!!!");
    }
    
}