package com.test.article.service;


import com.test.article.exception.NotFoundException;
import com.test.article.model.Comment;
import com.test.article.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Complete this
@Service
public class CommentService implements CrudRepository<Comment> {

    public static List<Comment> comments = new ArrayList<>();

    public List<Comment> findAll() {
        return comments.stream()
                .collect(Collectors.toList());
    }

    public Comment findById(int id) {
        return comments.stream()
                .filter(comment -> comment.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Comment not found!"));
    }

    public Comment save(Comment entity) {
        ArticleService.articles.stream()
                .filter(a -> entity.getArticle().getId() == a.getId())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Article not found!"))
                .getComments().add(entity);
        comments.add(entity);

        return entity;
    }

    public Comment update(int id, Comment entity) {
        ArticleService.articles.stream()
                .filter(article -> entity.getArticle().getId() == article.getId())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Article not found!"));

        Comment comment = comments.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Comment not found!"));
        comment.setEmail(entity.getEmail());
        comment.setText(entity.getText());

        return comment;
    }

    public void delete(int id) {
        Optional<Comment> comment = comments.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        comment.ifPresent(c -> {
            comments.remove(c);
            ArticleService.articles.stream()
                    .filter(a -> a.getComments().contains(c))
                    .forEach(a -> a.getComments().remove(c));
        });
        comment.orElseThrow(() -> new NotFoundException("Comment not found!"));
    }

}