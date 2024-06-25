package com.webber.jogging.controller;

import com.webber.jogging.domain.Shoes;
import com.webber.jogging.domain.User;
import com.webber.jogging.service.ShoesService;
import com.webber.jogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(path="/shoes", produces = "application/json")
    public ResponseEntity<Shoes> createShoes(@RequestBody Shoes shoes) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        shoes.setUser(user);
        Shoes created = shoesService.create(shoes);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path = "/shoes/{id}", produces = "application/json")
    public ResponseEntity<Shoes> updateShoes(@RequestBody Shoes shoes, @PathVariable long id) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        shoes.setUser(user);
        Shoes updated = shoesService.save(shoes);
        return ResponseEntity.ok(updated);
    }
}
