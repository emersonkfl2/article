package com.test.article.service;


import com.test.article.model.Comment;
import com.test.article.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
               .collect(Collectors.toList()).get(0);
    }

    public Comment save(Comment entity) {
        comments.add(entity);
        return entity;
    }

    public Comment update(int id, Comment entity) {
        return comments.stream()
                .filter(comment -> comment.getId() == id)
                .collect(Collectors.toList()).get(0);

    }

    public void delete(int id) {
        comments.removeIf(comment -> comment.getId() == id);
    }
}