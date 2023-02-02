package com.test.article.service;

import com.test.article.exception.NotFoundException;
import com.test.article.model.Article;
import com.test.article.model.Comment;
import com.test.article.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Complete this
@Service
public class ArticleService implements CrudRepository<Article> {

    public static List<Article> articles = new ArrayList<>();

    private CommentService commentService;

    @Autowired
    public ArticleService(CommentService service) {
        this.commentService = service;
    }

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
        if (!entity.getComments().isEmpty()) {
            entity.getComments().stream()
                    .forEach(commentService::save);
        }
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
        Optional<Article> article = articles.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
        article.ifPresentOrElse(a -> {
                    List<Comment> commentsToRemove = a.getComments().stream()
                            .filter(c -> c.getArticle().getId() == a.getId())
                            .collect(Collectors.toList());
                    commentsToRemove.forEach(c -> commentService.delete(c.getId()));
                    articles.remove(a);
                },
                () -> {
                    throw new NotFoundException("Article not found!");
                });
    }
}