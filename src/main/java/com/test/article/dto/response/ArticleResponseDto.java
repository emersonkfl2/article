package com.test.article.dto.response;

import com.test.article.constants.ArticleType;

import java.io.Serializable;
import java.util.List;

public class ArticleResponseDto implements Serializable {

	private int id;

	private String title;

	private String body;

	private ArticleType type;

	private List<CommentResponseDto> comments;

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

	public List<CommentResponseDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentResponseDto> comments) {
		this.comments = comments;
	}

}