package org.mvnsearch.rsocketinterface;

import org.mvnsearch.User;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.service.RSocketExchange;
import reactor.core.publisher.Mono;

@RSocketExchange
public interface HelloService {

    @RSocketExchange("hello")
    Mono<String> hello(@Payload String name);


    @RSocketExchange("findUserById")
    Mono<User> findUserById(@Payload Integer id);
}
