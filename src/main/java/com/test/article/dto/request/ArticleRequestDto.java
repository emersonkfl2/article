package com.test.article.dto.request;

import com.test.article.constants.ArticleType;
import com.test.article.model.Article;
import com.test.article.model.Comment;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleRequestDto implements Serializable {

    private int id;
    @NotBlank(message = "Title text can't be empty!")
    private String title;
    @NotBlank(message = "Body text can't be empty!")
    private String body;
    private ArticleType type;

    public ArticleRequestDto() {
    }

    public ArticleRequestDto(int id, String title, String body, ArticleType type, List<CommentRequestDto> comments) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.type = type;
        this.comments = comments;
    }

    private List<CommentRequestDto> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    public List<CommentRequestDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentRequestDto> comments) {
        this.comments = comments;
    }

    public Article toEntity() {
        Article article = new Article();
        article.setId(this.id);
        article.setTitle(this.title);
        article.setBody(this.body);
        article.setType(this.type);
        if (this.comments != null) {
            List<Comment> commentEntities = this.comments.stream()
                    .map(CommentRequestDto::toEntity)
                    .collect(Collectors.toList());
            article.setComments(commentEntities);
        }
        return article;
    }

}