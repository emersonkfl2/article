package com.test.article.controller;

import com.test.article.dto.ArticleDto;
import com.test.article.mapper.ArticleMapper;
import com.test.article.model.Article;
import com.test.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// TODO: Complete this
@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

  private ArticleService service;
  private ArticleMapper mapper;

  @Autowired
  public ArticleController(ArticleService service, ArticleMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping(value = "/get")
  public ResponseEntity<List<ArticleDto>> findAllArticles() {
    List<Article> article = service.findAll();
    List<ArticleDto> articleDto = mapper.toArticleDtoList(article);
    return new ResponseEntity<>(articleDto, HttpStatus.OK);
  }

  @GetMapping(value = "/get-by-id/{id}")
  public ResponseEntity<ArticleDto> findArticleById(@PathVariable int id) {
    Article article = service.findById(id);
    return new ResponseEntity<>(mapper.toDto(article), HttpStatus.OK);
  }

  @PostMapping(value = "/save")
  public ResponseEntity<ArticleDto> saveArticle(@Valid @RequestBody ArticleDto articleDto) {
    Article article = mapper.toEntity(articleDto);
    Article savedArticle = service.save(article);
    return new ResponseEntity<>(mapper.toDto(savedArticle), HttpStatus.CREATED);
  }

  @PutMapping(value = "/update-by-id/{id}")
  public ResponseEntity<ArticleDto> updateArticle(@PathVariable int id, @Valid @RequestBody ArticleDto articleDto) {
    Article updatedArticle = service.update(id, mapper.toEntity(articleDto));
    return new ResponseEntity<>(mapper.toDto(updatedArticle), HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete-by-id/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}