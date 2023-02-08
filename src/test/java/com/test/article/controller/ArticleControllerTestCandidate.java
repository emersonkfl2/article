package com.test.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.article.dto.request.ArticleRequestDto;
import com.test.article.model.Article;
import com.test.article.service.ArticleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static com.test.article.constants.ArticleType.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(articles.size())));
    }

    @Test
    public void shouldRetrieveArticleById() throws Exception {
        int id = articles.get(0).getId();
        Article article = articles.get(0);
        this.mockMvc.perform(get("/articles/" + id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(article.getId())))
                .andExpect(jsonPath("$.title", is(article.getTitle())))
                .andExpect(jsonPath("$.body", is(article.getBody())))
                .andExpect(jsonPath("$.type", is(article.getType().name())));
    }

    @Test
    public void shouldThrowAnErroWhenArticleIsNotFound() throws Exception {
        int id = articles.size() + 1;
        this.mockMvc.perform(get("/articles/" + id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.msg", is("Article not found!")));
    }

    @Test
    public void shouldSaveAnArticleWhenIdIsNotUsed_thenReturn201() throws Exception {
        ArticleRequestDto articleRequest = new ArticleRequestDto();
        articleRequest.setId(5);
        articleRequest.setTitle("Example Article");
        articleRequest.setBody("This is an example article body.");
        articleRequest.setType(CASE_STUDY);
        articleRequest.setComments(newArrayList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String requestJson = mapper.writeValueAsString(articleRequest);

        this.mockMvc.perform(post("/articles").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldThrowAndErrorWhenIdIsInUse() throws Exception {
        ArticleRequestDto articleRequest = new ArticleRequestDto();
        articleRequest.setId(1);
        articleRequest.setTitle("Example Article");
        articleRequest.setBody("This is an example article body.");
        articleRequest.setType(CASE_STUDY);
        articleRequest.setComments(newArrayList());

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(articleRequest);

        this.mockMvc.perform(post("/articles").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteArticle() throws Exception {
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

