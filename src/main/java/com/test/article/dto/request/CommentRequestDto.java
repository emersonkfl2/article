package com.test.article.dto.request;

import com.test.article.exception.NotFoundException;
import com.test.article.model.Comment;

import java.io.Serializable;

public class CommentRequestDto implements Serializable{

	private int id;
	private String email;
	private String text;
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
//		entity.setArticle(this.article.toEntity());
		if (article != null){
			entity.setArticle(this.article.toEntity());
		} else {
			throw new NotFoundException("You must provide an article!");
		}
		return entity;
	}
}
