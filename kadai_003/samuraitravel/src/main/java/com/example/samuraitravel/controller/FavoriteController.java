package com.example.samuraitravel.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // 追加
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.samuraitravel.entity.House; // 追加
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // お気に入り登録(Ajax切り替え)
    @PostMapping("/toggle")
    @ResponseBody
    public boolean toggle(
        @RequestParam Integer houseId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Integer userId = userDetails.getUser().getId();
        
        if (favoriteService.isFavorite(userId, houseId)) {
        	favoriteService.remove(userId, houseId);
        	return false; // ハートにする
        	} else {
        		favoriteService.add(userId, houseId);
        return true; // ♥にする
    }
    }
    
    // ★ お気に入り一覧
    @GetMapping
    public String index(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model) {

        Integer userId = userDetails.getUser().getId();

        List<House> favoriteHouses =
                favoriteService.findFavoriteHouses(userId);

        model.addAttribute("favoriteHouses", favoriteHouses);

        return "favorites/index";
    }
}
