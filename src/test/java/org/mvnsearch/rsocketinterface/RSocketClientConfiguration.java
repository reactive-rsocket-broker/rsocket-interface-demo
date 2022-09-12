package org.mvnsearch.rsocketinterface;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.service.RSocketServiceProxyFactory;

import java.net.URI;

@TestConfiguration
public class RSocketClientConfiguration {

    @Bean(destroyMethod = "dispose")
    public RSocketRequester rsocketRequester() {
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();
        return RSocketRequester.builder().rsocketStrategies(strategies).websocket(URI.create("ws://localhost:8080/rsocket"));
    }

    @Bean
    public RSocketServiceProxyFactory rsocketServiceProxyFactory(RSocketRequester rsocketRequester) {
        return new RSocketServiceProxyFactory(rsocketRequester);
    }

    @Bean
    public HelloService helloService(RSocketServiceProxyFactory rsocketServiceProxyFactory) {
        return rsocketServiceProxyFactory.createClient(HelloService.class);
    }
}
