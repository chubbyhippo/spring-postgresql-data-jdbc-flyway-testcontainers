package com.example.bookservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:tc:postgresql:13:///")
class BookServiceApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void addBookToCatalog() {
        var bookToCreate = new Book(null, "The Witcher");

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Book.class)
                .value(book -> {
                    assertThat(book.id()).isNotNull();
                    assertThat(book.title()).isEqualTo(bookToCreate.title());
                });
    }

}
