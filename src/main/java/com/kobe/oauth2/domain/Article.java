package com.kobe.oauth2.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "title", nullable = false) // "title"라는 not null 컬럼과 매핑
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "author", nullable = false)
	private String author;

	@CreatedDate // 엔티티가 생성될 때 생성 시간 저장
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Builder // 빌더 패턴으로 객체 생성
	public Article(String author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
	}

	@Builder
	public Article(String author, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
