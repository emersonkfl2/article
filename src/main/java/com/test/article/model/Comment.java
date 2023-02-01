package com.test.article.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.article.dto.CommentDto;

import javax.persistence.*;
import java.util.List;

// TODO: Complete this
@Entity
@Table(name = "COMMENT")
public class Comment implements Comparable<Comment>{
	@Id
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String text;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "id_article", nullable = false)
	private Article article;

	public Comment(){}

	public Comment(int id, String email, String text, List<Comment> list) {
		this.id = id;
		this.email = email;
		this.text = text;
	}

    public Comment(int id, Article aticleId, String text, String randomAlphabetic) {
    }

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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public int compareTo(Comment comment) {
		return Integer.compare(this.id, comment.getId());
	}
}
