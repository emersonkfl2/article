package com.test.article.controller;

import com.test.article.dto.CommentDto;
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
  
  private CommentService service;
  @Autowired
  public CommentController(CommentService service) {
    this.service = service;
  }

  @GetMapping(value = "/get")
  public ResponseEntity<List<CommentDto>> findAllComments() {
    List<Comment> comment = service.findAll();
    List<CommentDto> commentDto = comment.stream()
            .map(Comment::toDto).collect(Collectors.toList());
    return new ResponseEntity<>(commentDto, HttpStatus.OK);
  }

  @GetMapping(value = "/get-by-id/{id}")
  public ResponseEntity<CommentDto> findCommentById(@PathVariable int id) {
    Comment comment = service.findById(id);
    return new ResponseEntity<>(comment.toDto(), HttpStatus.OK);
  }

  @PostMapping(value = "/save")
  public ResponseEntity<CommentDto> saveComment(@Valid @RequestBody CommentDto commentDto) {
    Comment comment= commentDto.toEntity();
    Comment savedComment = service.save(comment);
    return new ResponseEntity<>(savedComment.toDto(), HttpStatus.CREATED);
  }

  @PutMapping(value = "/update-by-id/{id}")
  public ResponseEntity<CommentDto> updateArticle(@PathVariable int id, @Valid @RequestBody CommentDto commentDto) {
    Comment updatedComment= service.update(id, commentDto.toEntity());
    return new ResponseEntity<>(updatedComment.toDto(), HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete-by-id/{id}")
  public ResponseEntity<Void> deleteComment(@PathVariable int id) {
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}