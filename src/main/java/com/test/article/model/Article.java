package com.test.article.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.article.constants.ArticleType;
import com.test.article.dto.ArticleDto;

import javax.persistence.*;
import java.util.List;

// TODO: Complete this
@Entity
@Table(name = "ARTICLE")
public class Article implements Comparable<Article> {

	@Id
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
  	private String title;

	@Column(nullable = false)
	private String body;

	@Column(nullable = false)
	private ArticleType type;

	@Column
	@JsonManagedReference
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;

	public Article updateProperties(Article entity){
		this.title = entity.getTitle();
		this.body = entity.getBody();
		this.type = entity.getType();
		return entity;
	}

	public Article() {
	}

	public Article(int id, String title, String body, ArticleType type, List<Comment> comments) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.type = type;
		this.comments = comments;
	}

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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int compareTo(Article article) {
		return Integer.compare(this.id, article.getId());
	}

}
