package com.test.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.article.model.Article;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.test.article.constants.ArticleType.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Before
    public void setUp() {
        Article article1 = new Article(1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
	    Article article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
	    Article article3 = new Article(3, randomAlphabetic(10), randomAlphabetic(200), LITERATURE_REVIEW, newArrayList());
		articles = newArrayList(article1, article2, article3);
    }

    @Test
    public void shouldRetrieveAllArticles() throws Exception {
        this.mockMvc.perform(get("/articles").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(articles.size())));
    }
}
