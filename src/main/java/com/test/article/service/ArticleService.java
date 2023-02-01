package com.test.article.service;

import com.test.article.exception.NotFoundException;
import com.test.article.model.Article;
import com.test.article.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Complete this
@Service
public class ArticleService implements CrudRepository<Article> {

    public static List<Article> articles = new ArrayList<>();

    public List<Article> findAll() {
        return articles.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public Article findById(int id) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Article not found!"));
    }

    public Article save(Article entity) {
        articles.add(entity);
        return entity;
    }

    public Article update(int id, Article entity) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .map(article -> {
                    article.updateProperties(entity);
                    return article;
                })
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Article not found!"));
    }

    public void delete(int id) {
        Optional<Article> article = articles.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
        article.ifPresentOrElse(articles::remove,
                () -> {throw new NotFoundException("Article not found!");});
    }

}