package com.example.samuraitravel.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final HouseRepository houseRepository;

    public ReviewController(
        ReviewRepository reviewRepository,
        HouseRepository houseRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.houseRepository =  houseRepository;
    }

    @PostMapping("/{houseId}")
    public String store(
        @PathVariable Integer houseId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        ReviewForm form
    ) {
    	
    	// 1. ログインユーザー取得
        User user = userDetails.getUser();
        House house = houseRepository.getReferenceById(houseId);
        
     // ★ 二重投稿チェック（最重要）
        if (reviewRepository.existsByUserAndHouse(user, house)) {
            return "redirect:/houses/" + houseId;
        } 

        Review review = new Review();
        review.setHouse(house);
        review.setUser(user); 
        review.setRating(form.getRating());
        review.setComment(form.getComment());

        reviewRepository.save(review);

        return "redirect:/houses/" + houseId; 
    } 
    
    @PostMapping("/{id}/update")
	public String update(
	        @PathVariable Integer id,
	        @AuthenticationPrincipal UserDetailsImpl userDetails,
	        ReviewForm form) {

	    Review review = reviewRepository.getReferenceById(id);
	    
	 // ★ 本人チェック
        if (!review.getUser().getId().equals(userDetails.getUser().getId())) {
            return "redirect:/houses/" + review.getHouse().getId();
        }

        review.setRating(form.getRating());
        review.setComment(form.getComment());

        reviewRepository.save(review);

        return "redirect:/houses/" + review.getHouse().getId();
    }
    
    @GetMapping("/{id}/edit")
    public String edit(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model) {

        Review review = reviewRepository.getReferenceById(id);

        // ★ 本人チェック
        if (!review.getUser().getId().equals(userDetails.getUser().getId())) {
            return "redirect:/houses/" + review.getHouse().getId();
        }

        ReviewForm form = new ReviewForm();
        form.setRating(review.getRating());
        form.setComment(review.getComment());

        model.addAttribute("review", review);
        model.addAttribute("reviewForm", form);

        return "reviews/edit";
    }
    
    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
    	
    	Review review = reviewRepository.getReferenceById(id);

        // ★ 自分のレビュー以外は削除不可
        if (!review.getUser().getId().equals(userDetails.getUser().getId())) {
        	return "redirect:/houses/" + review.getHouse().getId();
        }
        
        // 更新処理
        Integer houseId = review.getHouse().getId();
        reviewRepository.delete(review);

        return "redirect:/houses/" + houseId;
    }

    @GetMapping("/new/{houseId}")
    public String newReview(
        @PathVariable Integer houseId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        Model model
    ) {
        House house = houseRepository.getReferenceById(houseId);

        model.addAttribute("house", house); 
        model.addAttribute("reviewForm", new ReviewForm()); 

        return "reviews/new"; 
    }
}    
    