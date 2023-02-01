package com.test.article.mapper;

import com.test.article.dto.ArticleDto;
import com.test.article.model.Article;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {
    private final ModelMapper mapper;

    public ArticleMapper(ModelMapper mapper){
        this.mapper = mapper;
    }

    public Article toEntity(ArticleDto articleDto){
        return mapper.map(articleDto, Article.class);
    }

    public ArticleDto toDto(Article article){
        return mapper.map(article, ArticleDto.class);
    }

    public List<ArticleDto> toArticleDtoList(List<Article> articles){
        return articles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
