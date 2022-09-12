RSocket interface demo
==========================================

The Spring Framework lets you define an RSocket service as a Java interface with annotated methods for RSocket exchanges.
You can then generate a proxy that implements this interface and performs the exchanges.
This helps to simplify RSocket remote access by wrapping the use of the underlying RSocketRequester.

# Getting Started

* Create RSocket Service provider

```java

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

```

* Create RSocket Interface to talk with RSocket Service provider

```java
public interface HelloService {

    @RSocketExchange("hello")
    Mono<String> hello(@Payload String name);


    @RSocketExchange("findUserById")
    Mono<User> findUserById(@Payload Integer id);
}
``` 

* Create proxy for RSocket Interface

```java
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
}
```

# References

* Spring RSocket: https://docs.spring.io/spring-framework/docs/6.0.0-SNAPSHOT/reference/html/web-reactive.html#rsocket
* RSocket interface: https://docs.spring.io/spring-framework/docs/6.0.0-SNAPSHOT/reference/html/web-reactive.html#rsocket-interface
* Spring HTTP Interface: https://docs.spring.io/spring-framework/docs/6.0.0-M5/reference/html/integration.html#rest-http-interface
