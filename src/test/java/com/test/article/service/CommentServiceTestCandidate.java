package com.test.article.service;

import com.google.common.collect.Lists;
import com.test.article.exception.NotFoundException;
import com.test.article.model.Article;
import com.test.article.model.Comment;
import org.junit.*;
import java.util.*;
//import articles.exception.*;
//import articles.model.*;
//import static articles.constants.ArticleType.*;
import static com.test.article.constants.ArticleType.CASE_STUDY;
import static com.test.article.constants.ArticleType.EMPIRICAL_STUDY;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static com.google.common.collect.Lists.newArrayList;

// TODO: Add more tests
public class CommentServiceTestCandidate {

    private CommentService commentService = new CommentService();
    private List<Comment> comments;
    private List<Article> articles;

    @Before
    public void setUp() {
        Article article1 = new Article(1,randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Comment comment1 = new Comment(1,article1,"test@gmail.com",randomAlphabetic(100));
        Comment comment2 = new Comment(2,article1,"test2@gmail.com",randomAlphabetic(100));
        Comment comment3 = new Comment(3,article1,"test3@gmail.com",randomAlphabetic(100));

        Article article2 = new Article(2,randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        Comment comment4 = new Comment(4,article2,"test4@gmail.com",randomAlphabetic(100));
        Comment comment5 = new Comment(5,article2,"test5@gmail.com",randomAlphabetic(100));
        Comment comment6 = new Comment(6,article2,"test6@gmail.com",randomAlphabetic(100));

        comments = newArrayList(comment1, comment2, comment3, comment4, comment5, comment6);
        articles = newArrayList(article1, article2);
        ArticleService.articles = articles;
        CommentService.comments = comments;
    }

    @Test
    public void shouldFindAllComments() {
        List<Comment> commentsResultList = commentService.findAll();

        assertThat(commentsResultList).hasSize(comments.size()).isEqualTo(comments);
    }

    @Test
    public void shouldFindCommentById() {
        int id = 1;
        Comment expectedComment = comments.get(0);
        Comment actualComment = commentService.findById(id);

        assertThat(expectedComment).isEqualTo(actualComment);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenCommentIdNotFound() {
        int id = 100;
        assertThrows(NotFoundException.class, () -> commentService.findById(id));
    }

    @Test
    public void shouldSaveCommentWhenIdIsNotInUse() {
        int id = comments.size() + 1;
        Comment newComment = new Comment(1, articles.get(0),"test@gmail.com",randomAlphabetic(100));
        Comment savedComment = commentService.save(newComment);

        assertThat(savedComment).isEqualTo(newComment);
        assertThat(comments).hasSize(comments.size());
        assertThat(comments).contains(newComment);
    }

    @Test
    public void shouldUpdateAnCommentIfExists() {
        Comment updatedComment= new Comment(1, articles.get(0), "testUpdated@gmail.com", "updated text comment");
        Comment result = commentService.update(1, updatedComment);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getEmail()).isEqualTo("testUpdated@gmail.com");
        assertThat(result.getText()).isEqualTo("updated text comment");
    }

    @Test
    public void shouldDeleteACommentIfExists() {
        int id = comments.get(0).getId();
        int initialSize = comments.size();
        commentService.delete(id);

        assertThat(comments).hasSize(initialSize - 1);
        assertThat(comments.stream().noneMatch(comment -> comment.getId() == id)).isTrue();
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenCommentIdNotFoundInDelete() {
        int id = comments.size() + 1;

        assertThrows(NotFoundException.class, () -> commentService.delete(id));
    }
}
