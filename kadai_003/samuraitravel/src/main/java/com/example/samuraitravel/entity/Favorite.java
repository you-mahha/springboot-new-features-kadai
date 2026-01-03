package com.example.samuraitravel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne; // お気に入り一覧に必要
import jakarta.persistence.Table; 

@Entity
@Table(name = "favorites")
public class Favorite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// ユーザーID
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// 民宿（house）ID
	@ManyToOne
	@JoinColumn(name = "house_id", nullable = false)
	private House house;

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public House getHouse() {
		return house;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setHouse(House house) {
		this.house = house;
	}
}
