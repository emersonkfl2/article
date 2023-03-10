package com.test.article.controller;

import com.test.article.model.Article;
import com.test.article.model.Comment;
import com.test.article.service.ArticleService;
import com.test.article.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

import static com.test.article.constants.ArticleType.CASE_STUDY;
import static com.test.article.constants.ArticleType.EMPIRICAL_STUDY;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// TODO: Add more tests
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTestCandidate {
    private List<Comment> comments;
    private List<Article> articles;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        Article article1 = new Article(1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Article article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());

        Comment comment1 = new Comment(1, article1, "test@gmail.com", randomAlphabetic(100));
        Comment comment2 = new Comment(2, article1, "test2@gmail.com", randomAlphabetic(100));
        Comment comment3 = new Comment(3, article2, "test3@gmail.com", randomAlphabetic(100));

        comments = newArrayList(comment1, comment2, comment3);
        articles = newArrayList(article1, article2);
        ArticleService.articles = articles;
        CommentService.comments = comments;
    }

    @Test
    public void shouldRetrieveAllComments() throws Exception {
        this.mockMvc.perform(get("/comments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(comments.size())));
    }

    @Test
    public void shouldReturnCommentWhenValidIdProvided() throws Exception {
        int id = comments.get(0).getId();
        Comment comment = comments.get(0);
        this.mockMvc.perform(get("/comments/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comment.getId())))
                .andExpect(jsonPath("$.email", is(comment.getEmail())))
                .andExpect(jsonPath("$.text", is(comment.getText())));
    }

    @Test
    public void shouldDeleteComment() throws Exception {
        Comment comment = comments.get(0);
        int id = comment.getId();

        this.mockMvc.perform(delete("/comments/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<Comment> result = comments.stream()
                .filter(c -> c.getId() == id)
                .findFirst();

        assertFalse(result.isPresent());
    }

}