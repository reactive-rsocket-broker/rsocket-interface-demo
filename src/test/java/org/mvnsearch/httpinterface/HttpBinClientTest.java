package org.mvnsearch.httpinterface;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class HttpBinClientTest {
    private static HttpBinClient httpBinClient;

    @BeforeAll
    public static void setUp() throws Exception {
        WebClient webClient = WebClient.builder().build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient)).build();
        httpBinClient = httpServiceProxyFactory.createClient(HttpBinClient.class);
    }

    @Test
    public void testClient() {
        System.out.println(httpBinClient.myIp().origin());
        System.out.println(httpBinClient.post("Hello").data());
    }
}
