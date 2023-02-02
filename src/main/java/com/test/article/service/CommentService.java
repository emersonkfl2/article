package com.test.article.service;


import com.test.article.exception.NotFoundException;
import com.test.article.model.Article;
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
                .sorted()
                .collect(Collectors.toList());
    }

    public Comment findById(int id) {
       return comments.stream()
               .filter(comment -> comment.getId() == id)
               .findFirst()
               .orElseThrow(() -> new NotFoundException("Comment not found!"));
    }

    public Comment save(Comment entity) {
        comments.add(entity);
        return entity;
    }

    public Comment update(int id, Comment updatedComment) {
        Comment comment = comments.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Comment not found!"));
        comment.setEmail(updatedComment.getEmail());
        comment.setText(updatedComment.getText());

        return comment;
    }

    public void delete(int id) {
        Optional<Comment> comment = comments.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        comment.ifPresentOrElse(comments::remove,
                () -> {throw new NotFoundException("Comment not found!");});
    }
}