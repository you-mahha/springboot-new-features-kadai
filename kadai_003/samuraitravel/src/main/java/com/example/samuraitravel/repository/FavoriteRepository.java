package com.example.samuraitravel.repository;

import java.util.List; // 追加

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	
	boolean existsByUserAndHouse(User user, House house);

    // すでにお気に入り登録されているか確認
    void deleteByUserAndHouse(User user, House house);
    
 // ★ 追加：ユーザーのお気に入り一覧 userIdでお気に入りを全て取る
    List<Favorite> findByUser(User user);
}
