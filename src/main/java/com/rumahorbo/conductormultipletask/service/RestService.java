package com.rumahorbo.conductormultipletask.service;

import com.rumahorbo.conductormultipletask.model.Product;
import com.rumahorbo.conductormultipletask.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestService {

    private final WebClient client = WebClient.create("https://dummyjson.com");

    public Mono<User> createUser(User user) {
        return this.client.post()
                .uri("/users/add")
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<Product> createProduct(Product product) {
        return this.client.post()
                .uri("/products/add")
                .body(Mono.just(product), Product.class)
                .retrieve()
                .bodyToMono(Product.class);
    }
}
