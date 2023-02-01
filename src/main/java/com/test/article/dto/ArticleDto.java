package com.test.article.dto;

import com.test.article.model.Article;
import com.test.article.constants.ArticleType;
import com.test.article.model.Comment;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleDto implements Serializable {

	private int id;

	@NotBlank(message = "Title text can't be empty!")
	private String title;

	@NotBlank(message = "Body text can't be empty!")
	private String body;

	private ArticleType type;

	private List<CommentDto> comments;

	//TODO fazer lista de comentarios

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

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}


}