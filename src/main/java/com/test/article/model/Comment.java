package com.test.article.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.test.article.dto.request.CommentRequestDto;
import com.test.article.dto.response.CommentResponseDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

// TODO: Complete this
@Entity
@Table(name = "comment")
public class Comment implements Comparable<Comment>{
	@Id
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
	@NotBlank(message = "Email can't be empty!")
	@Email(message = "You must provide a valid email!")
	private String email;

	@NotBlank(message = "Text can't be empty!")
	@Column(nullable = false)
	private String text;


	//TODO verificar a passagem do id_article ao invés da referencia inteira
	@ManyToOne
	@NotBlank
	@JsonBackReference
	@JoinColumn(name = "id_article", nullable = false)
	private Article article;

	public Comment(){}

	public Comment(int id, String email, String text) {
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

	public CommentResponseDto toDto() {
		CommentResponseDto commentResponseDto = new CommentResponseDto();
		commentResponseDto.setId(this.id);
		commentResponseDto.setEmail(this.email);
		commentResponseDto.setText(this.text);
//		commentResponseDto.setIdArticle(article.getId());
		return commentResponseDto;
	}
}
