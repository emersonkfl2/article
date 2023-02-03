package com.test.article.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.article.model.Comment;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CommentRequestDto implements Serializable{

	private int id;
	@NotBlank(message = "Email can't be empty!")
	@Email(message = "You must provide a valid email!")
	private String email;
	@NotBlank(message = "Text can't be empty!")
	private String text;
	@NotBlank
	private ArticleRequestDto article;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArticleRequestDto getArticle() {
		return article;
	}

	public void setArticle(ArticleRequestDto article) {
		this.article = article;
	}

	public Comment toEntity(){
		Comment entity = new Comment();
		entity.setId(this.id);
		entity.setEmail(this.email);
		entity.setText(this.text);
		entity.setArticle(this.article.toEntity());
		return entity;
	}
}
