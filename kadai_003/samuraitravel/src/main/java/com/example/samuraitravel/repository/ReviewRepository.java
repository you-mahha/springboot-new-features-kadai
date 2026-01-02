package com.example.samuraitravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;// 追加

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	// 民宿ごとのレビュー一覧(新しい順)
    List<Review> findByHouseOrderByCreatedAtDesc(House house);
    
    boolean existsByUserAndHouse(User user, House house);
}
