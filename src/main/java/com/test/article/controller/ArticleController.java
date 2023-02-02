package com.test.article.controller;

import com.test.article.dto.ArticleDto;
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

  private ArticleService service;

  @Autowired
  public ArticleController(ArticleService service) {
    this.service = service;
  }

  @GetMapping(value = "/get")
  public ResponseEntity<List<ArticleDto>> findAllArticles() {
    List<Article> article = service.findAll();
    List<ArticleDto> articleDto = article.stream()
            .map(Article::toDto).collect(Collectors.toList());
    return new ResponseEntity<>(articleDto, HttpStatus.OK);
  }

  @GetMapping(value = "/get-by-id/{id}")
  public ResponseEntity<ArticleDto> findArticleById(@PathVariable int id) {
    Article article = service.findById(id);
    return new ResponseEntity<>(article.toDto(), HttpStatus.OK);
  }

  @PostMapping(value = "/save")
  public ResponseEntity<ArticleDto> saveArticle(@Valid @RequestBody ArticleDto articleDto) {
    Article article = articleDto.toEntity();
    Article savedArticle = service.save(article);
    return new ResponseEntity<>(savedArticle.toDto(), HttpStatus.CREATED);
  }

  @PutMapping(value = "/update-by-id/{id}")
  public ResponseEntity<ArticleDto> updateArticle(@PathVariable int id, @Valid @RequestBody ArticleDto articleDto) {
    Article updatedArticle = service.update(id, articleDto.toEntity());
    return new ResponseEntity<>(updatedArticle.toDto(), HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete-by-id/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}