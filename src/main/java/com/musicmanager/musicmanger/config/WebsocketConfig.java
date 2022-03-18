package com.musicmanager.musicmanger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ConfigurationProperties
@EnableWebSocketMessageBroker
@Controller
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer{
	private static final Log log = LogFactory.getLog(WebsocketConfig.class);
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // TODO Auto-generated method stub
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/music");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    
    }

  
    
}
