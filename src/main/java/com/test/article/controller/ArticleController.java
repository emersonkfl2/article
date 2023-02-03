package com.test.article.controller;

import com.test.article.dto.request.ArticleRequestDto;
import com.test.article.model.Article;
import com.test.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Complete this
@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

  private final ArticleService service;

  @Autowired
  public ArticleController(ArticleService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<ArticleRequestDto>> findAllArticles() {
    List<Article> article = service.findAll();
    List<ArticleRequestDto> articleRequestDto = article.stream()
            .map(Article::toDto).collect(Collectors.toList());
    return new ResponseEntity<>(articleRequestDto, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ArticleRequestDto> findArticleById(@PathVariable int id) {
    Article article = service.findById(id);
    return new ResponseEntity<>(article.toDto(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ArticleRequestDto> saveArticle(@Valid @RequestBody ArticleRequestDto articleRequestDto) {
    Article savedArticle = service.save(articleRequestDto.toEntity());
    return new ResponseEntity<>(savedArticle.toDto(), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ArticleRequestDto> updateArticle(@PathVariable int id, @Valid @RequestBody ArticleRequestDto articleRequestDto) {
    Article updatedArticle = service.update(id, articleRequestDto.toEntity());
    return new ResponseEntity<>(updatedArticle.toDto(), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}