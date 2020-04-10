package com.webber.jogging.controller;

import com.webber.jogging.domain.Shoes;
import com.webber.jogging.domain.User;
import com.webber.jogging.service.ShoesService;
import com.webber.jogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jogging")
public class ShoesController {
    @Autowired
    private UserService userService;

    @Autowired
    private ShoesService shoesService;

    @GetMapping(path = "/shoes", produces = "application/json")
    public ResponseEntity<List<Shoes>> listAll() throws UserNotFoundException {
        User user = userService.getCurrentUser();
        List<Shoes> shoes = shoesService.getShoesForUser(user, false);
        return ResponseEntity.ok(shoes);
    }
}