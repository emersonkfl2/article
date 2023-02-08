package com.test.article.controller;

import com.test.article.dto.request.ArticleRequestDto;
import com.test.article.dto.response.ArticleResponseDto;
import com.test.article.model.Article;
import com.test.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> findAllArticles() {
        List<Article> article = service.findAll();
        List<ArticleResponseDto> articleResponseDto = article.stream()
                .map(Article::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(articleResponseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleResponseDto> findArticleById(@PathVariable int id) {
        Article article = service.findById(id);
        return new ResponseEntity<>(article.toDto(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> saveArticle(@Valid @RequestBody ArticleRequestDto articleRequestDto) {
        try {
            Article article = service.save(articleRequestDto.toEntity());
            return new ResponseEntity<>(article.toDto(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable int id, @Valid @RequestBody ArticleRequestDto articleRequestDto) {
        Article article = service.update(id, articleRequestDto.toEntity());
        return new ResponseEntity<>(article.toDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}