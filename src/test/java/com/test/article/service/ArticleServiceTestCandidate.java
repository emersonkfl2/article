package com.test.article.service;

import com.test.article.exception.NotFoundException;
import com.test.article.model.Article;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.test.article.constants.ArticleType.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


// TODO: Add more tests
public class ArticleServiceTestCandidate {

    private ArticleService service = new ArticleService();
    private List<Article> articles;

    @Before
    public void setUp() {
        Article article1 = new Article(1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Article article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        Article article3 = new Article(3, randomAlphabetic(10), randomAlphabetic(200), LITERATURE_REVIEW, newArrayList());
        Article article4 = new Article(4, randomAlphabetic(10), randomAlphabetic(200), METHODOLOGIC, newArrayList());
        Article article5 = new Article(5, randomAlphabetic(10), randomAlphabetic(200), THEORIC, newArrayList());
        Article article6 = new Article(6, randomAlphabetic(10), randomAlphabetic(200), THEORIC, newArrayList());
        articles = newArrayList(article1, article2, article3, article4, article5, article6);
        ArticleService.articles = articles;
    }

    @Test
    public void shouldFindAllArticles() {
        List<Article> articlesResultList = service.findAll();

        assertThat(articlesResultList).hasSize(articles.size()).isEqualTo(articles);
    }

    @Test
    public void shouldFindArticleById() {
        int id = 1;
        Article expectedArticle = articles.get(0);
        Article actualArticle = service.findById(id);

        assertThat(actualArticle).isEqualTo(expectedArticle);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenArticleIdNotFound() {
        int id = 100;
        assertThrows(NotFoundException.class, () -> service.findById(id));
    }

    @Test
    public void shouldSaveArticleWhenIdIsNotInUse() {
        int id = articles.size() + 1;
        Article newArticle = new Article(articles.size() + 1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Article savedArticle = service.save(newArticle);

        assertThat(savedArticle).isEqualTo(newArticle);
        assertThat(articles).hasSize(articles.size());
        assertThat(articles).contains(newArticle);
    }

    @Test
    public void shouldUpdateAnArticleIfExists() {
        Article updatedArticle = new Article(1, "updated title", "updated body", EMPIRICAL_STUDY, newArrayList());
        Article result = service.update(1, updatedArticle);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getTitle()).isEqualTo("updated title");
        assertThat(result.getBody()).isEqualTo("updated body");
        assertThat(result.getType()).isEqualTo(EMPIRICAL_STUDY);
    }

    @Test
    public void shouldDeleteAnArticleIfExists() {
        int id = articles.get(0).getId();
        int initialSize = articles.size();
        service.delete(id);

        assertThat(articles).hasSize(initialSize - 1);
        assertThat(articles.stream().noneMatch(article -> article.getId() == id)).isTrue();
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenArticleIdNotFoundInDelete() {
        int id = articles.size() + 1;

        assertThrows(NotFoundException.class, () -> service.delete(id));
    }


}
