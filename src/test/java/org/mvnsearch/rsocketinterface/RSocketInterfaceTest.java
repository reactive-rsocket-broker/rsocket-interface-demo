package org.mvnsearch.rsocketinterface;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mvnsearch.User;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.service.RSocketServiceProxyFactory;

import java.net.URI;

public class RSocketInterfaceTest {
    private static RSocketRequester requester;
    private static HelloService helloService;

    @BeforeAll
    public static void setUp() throws Exception {
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();
        requester = RSocketRequester.builder().rsocketStrategies(strategies).websocket(URI.create("ws://localhost:8080/rsocket"));
        RSocketServiceProxyFactory factory = new RSocketServiceProxyFactory(requester);
        factory.afterPropertiesSet();
        helloService = factory.createClient(HelloService.class);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        requester.rsocketClient().dispose();
    }

    @Test
    public void testHello() {
        String welcome = helloService.hello("Jackie").block();
        System.out.println(welcome);
    }

    @Test
    public void testFindUserById() {
        User user = helloService.findUserById(1).block();
        System.out.println(user.getEmail());
    }
}
