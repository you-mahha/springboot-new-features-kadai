package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	
	boolean existsByUserIdAndHouseId(Integer userId, Integer houseId);

    // すでにお気に入り登録されているか確認
    void deleteByUserIdAndHouseId(Integer userId, Integer houseId);
}
