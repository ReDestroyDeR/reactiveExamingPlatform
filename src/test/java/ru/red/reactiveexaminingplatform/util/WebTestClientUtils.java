package ru.red.reactiveexaminingplatform.util;

import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class WebTestClientUtils {
    public static <T> WebTestClient.ResponseSpec postInBodyGetExchange(WebTestClient client, String url, T payload) {
        return client.post()
                .uri(url)
                .body(Mono.just(payload), payload.getClass())
                .exchange();
    }

    public static <T, R> R postInBodyGetResponse(WebTestClient client, String url, T payload, Class<R> expect) {
        return postInBodyGetExchange(client, url, payload)
                .expectBody(expect)
                .returnResult()
                .getResponseBody();
    }
}
