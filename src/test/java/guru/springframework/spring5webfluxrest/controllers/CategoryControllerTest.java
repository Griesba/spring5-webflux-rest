package guru.springframework.spring5webfluxrest.controllers;


import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryControllerTest {

    private WebTestClient webTestClient;
    private CategoryRepository categoryRepository;
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(
                        Flux.just(
                                Category.builder().description("cat1").build(),
                                Category.builder().description("cat2").build()
                        )
                );

        webTestClient.get()
                .uri(CategoryController.BASE_URL)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getOne() {
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().id("someId").build()));

        Category category = webTestClient.get()
                .uri(CategoryController.BASE_URL + "/someId")
                .exchange()
                .expectBody(Category.class)
                .returnResult().getResponseBody();

        assertNotNull(category);
        assertEquals("someId", category.getId());
    }
}