package com.webber.jogging.gear;

import com.webber.jogging.security.UserNotFoundException;
import com.webber.jogging.user.User;
import com.webber.jogging.user.UserService;
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
public class GearController {
    @Autowired
    private UserService userService;

    @Autowired
    private GearService gearService;

    @GetMapping(path = "/gear", produces = "application/json")
    public ResponseEntity<List<Gear>> listAll() throws UserNotFoundException {
        User user = userService.getCurrentUser();
        List<Gear> shoes = gearService.getGearForUser(user, false);
        return ResponseEntity.ok(shoes);
    }

    @PostMapping(path="/gear", produces = "application/json")
    public ResponseEntity<Gear> createGear(@RequestBody Gear gear) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        gear.setUser(user);
        Gear created = gearService.create(gear);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path = "/gear/{id}", produces = "application/json")
    public ResponseEntity<Gear> updateGear(@RequestBody Gear gear, @PathVariable long id) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        gear.setUser(user);
        Gear updated = gearService.save(gear);
        return ResponseEntity.ok(updated);
    }
}
