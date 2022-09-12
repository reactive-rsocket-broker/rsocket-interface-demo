package org.mvnsearch;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class RSocketController {

    @MessageMapping("hello")
    public Mono<String> hello(String name) {
        return Mono.just("Hello " + name);
    }

    @MessageMapping("findUserById")
    public Mono<User> findUserById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("name");
        user.setEmail("reginaldo_stuckeyz@calendars.fl");
        return Mono.just(user);
    }
}
