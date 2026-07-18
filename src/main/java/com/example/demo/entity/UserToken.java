package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_tokens")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(length = 1000)
    private String token;

    private LocalDateTime issuedAt;

    private LocalDateTime expiryAt;

    private Boolean active;

	public UserToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserToken(Long id, String username, String token, LocalDateTime issuedAt, LocalDateTime expiryAt,
			Boolean active) {
		super();
		this.id = id;
		this.username = username;
		this.token = token;
		this.issuedAt = issuedAt;
		this.expiryAt = expiryAt;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(LocalDateTime issuedAt) {
		this.issuedAt = issuedAt;
	}

	public LocalDateTime getExpiryAt() {
		return expiryAt;
	}

	public void setExpiryAt(LocalDateTime expiryAt) {
		this.expiryAt = expiryAt;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
    
    
}
