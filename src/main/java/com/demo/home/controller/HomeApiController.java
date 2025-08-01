package com.demo.home.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.home.model.HomePageViewModel;
import com.demo.home.service.impl.HomeService;
import com.demo.user.entity.User;

@RestController
@RequestMapping("/api/home")
public class HomeApiController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private HttpSession session;

    @GetMapping("")
    public ResponseEntity<?> getHomeData(@RequestParam(required = false) String targetMonth) {

        User user = (User) session.getAttribute("user");

        // if (user == null) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        // }

        HomePageViewModel viewModel = homeService.buildHomePage("T00001", targetMonth);

        return ResponseEntity.ok(viewModel);
    }
}
