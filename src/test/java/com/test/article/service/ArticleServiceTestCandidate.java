package com.test.article.service;

import com.test.article.model.Article;
import com.test.article.service.ArticleService;
import org.junit.*;
import java.util.*;

import static com.test.article.constants.ArticleType.*;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static com.google.common.collect.Lists.newArrayList;

// TODO: Add more tests
public class ArticleServiceTestCandidate {

	private ArticleService service = new ArticleService();
    private List<Article> articles;

	@Before
	public void setUp() {
        Article article1 = new Article(1,randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
	    Article article2 = new Article(2,randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
	    Article article3 = new Article(3,randomAlphabetic(10), randomAlphabetic(200), LITERATURE_REVIEW, newArrayList());
	    Article article4 = new Article(4,randomAlphabetic(10), randomAlphabetic(200), METHODOLOGIC, newArrayList());
	    Article article5 = new Article(5,randomAlphabetic(10), randomAlphabetic(200), THEORIC, newArrayList());
	    Article article6 = new Article(6,randomAlphabetic(10), randomAlphabetic(200), THEORIC, newArrayList());
		articles = newArrayList(article1, article2, article3, article4, article5, article6);
		ArticleService.articles = articles;
	}

	@Test
	public void shouldFindAllArticles() {
		List<Article> articlesResultList = service.findAll();

		assertThat(articlesResultList).hasSize(articles.size()).isEqualTo(articles);
	}
}
