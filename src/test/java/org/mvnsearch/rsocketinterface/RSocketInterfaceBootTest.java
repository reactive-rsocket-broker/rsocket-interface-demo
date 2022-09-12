package org.mvnsearch.rsocketinterface;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(RSocketClientConfiguration.class)
public class RSocketInterfaceBootTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void testHello() {
        String welcome = helloService.hello("Jackie").block();
        System.out.println(welcome);
    }

}
