package com.test.article.service;

import com.test.article.exception.BadRequestError;
import com.test.article.exception.NotFoundException;
import com.test.article.model.Article;
import com.test.article.model.Comment;
import com.test.article.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        if (articles.stream().anyMatch(a -> a.getId() == entity.getId())) {
            throw new NotFoundException("An article with this ID already exists!");
        }
        List<Comment> comments = new ArrayList<>();
        for (Comment comment : entity.getComments()) {
            if (comment.getArticle().getId() == entity.getId()) {
                comments.add(comment);
                CommentService.comments.add(comment);
            } else {
                throw new NotFoundException("The article ID in the comment does not match the article ID!");
            }
        }
        entity.setComments(comments);
        articles.add(entity);
        return entity;
    }

    public Article update(int id, Article updatedArticle) {
        Article article = articles.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Article not found!"));

        article.setTitle(updatedArticle.getTitle());
        article.setBody(updatedArticle.getBody());
        article.setType(updatedArticle.getType());

        return article;
    }

    public void delete(int id) {
        articles.stream()
                .filter(article -> article.getId() == id)
                .findFirst()
                .ifPresentOrElse(article -> {
                    article.getComments().stream()
                            .filter(comment -> comment.getArticle().getId() == article.getId())
                            .forEach(comment -> CommentService.comments.remove(comment));
                    articles.remove(article);
                }, () -> {
                    throw new NotFoundException("Article not found!");
                });
    }

}