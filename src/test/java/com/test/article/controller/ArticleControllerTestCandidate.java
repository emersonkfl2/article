package com.test.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.article.constants.ArticleType;
import com.test.article.dto.request.ArticleRequestDto;
import com.test.article.model.Article;
import com.test.article.model.Comment;
import com.test.article.service.ArticleService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.test.article.constants.ArticleType.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// TODO: Add more tests
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTestCandidate {

    private List<Article> articles;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ArticleController controller;

    @Mock
    private ArticleService service;

    @Before
    public void setUp() {
        Article article1 = new Article(1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Article article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        Article article3 = new Article(3, randomAlphabetic(10), randomAlphabetic(200), LITERATURE_REVIEW, newArrayList());
        articles = newArrayList(article1, article2, article3);
        ArticleService.articles = articles;
    }

    @Test
    public void shouldRetrieveAllArticles() throws Exception {
        this.mockMvc.perform(get("/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(articles.size())));
    }

    @Test
    public void shouldRetrieveArticleById() throws Exception {
        int id = articles.get(0).getId();
        Article article = articles.get(0);
        this.mockMvc.perform(get("/articles/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(article.getId())))
                .andExpect(jsonPath("$.title", is(article.getTitle())))
                .andExpect(jsonPath("$.body", is(article.getBody())))
                .andExpect(jsonPath("$.type", is(article.getType().name())));
    }

    @Test
    public void shouldDeleteComment() throws Exception {
        Article article = articles.get(0);
        int id = article.getId();

        this.mockMvc.perform(delete("/articles/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<Article> result = articles.stream()
                .filter(a -> a.getId() == id)
                .findFirst();

        assertFalse(result.isPresent());
    }

}

