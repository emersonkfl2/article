package com.test.article.controller;

import com.test.article.dto.request.CommentRequestDto;
import com.test.article.model.Comment;
import com.test.article.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Complete this
@RestController
@RequestMapping(value = "comments")
public class CommentController {

    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CommentRequestDto>> findAllComments() {
        List<Comment> comment = service.findAll();
        List<CommentRequestDto> commentRequestDto = comment.stream()
                .map(Comment::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(commentRequestDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommentRequestDto> findCommentById(@PathVariable int id) {
        Comment comment = service.findById(id);
        return new ResponseEntity<>(comment.toDto(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentRequestDto> saveComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = service.save(commentRequestDto.toEntity());
        return new ResponseEntity<>(comment.toDto(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CommentRequestDto> updateArticle(@PathVariable int id, @Valid @RequestBody CommentRequestDto commentRequestDto) {
        Comment updatedComment = service.update(id, commentRequestDto.toEntity());
        return new ResponseEntity<>(updatedComment.toDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}