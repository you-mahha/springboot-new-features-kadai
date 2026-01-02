package com.example.samuraitravel.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // お気に入り登録
    @PostMapping("/add")
    public String add(
        @RequestParam Integer houseId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        favoriteService.add(userDetails.getUser().getId(), houseId);
        return "redirect:/houses/" + houseId;
    }

    // お気に入り解除
    @PostMapping("/remove")
    public String remove(
        @RequestParam Integer houseId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        favoriteService.remove(userDetails.getUser().getId(), houseId);
        return "redirect:/houses/" + houseId;
    }
}
