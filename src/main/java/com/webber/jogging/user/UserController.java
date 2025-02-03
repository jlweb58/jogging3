package com.webber.jogging.user;

import com.webber.jogging.security.ChangePasswordResponse;
import com.webber.jogging.security.PasswordChangeRequest;
import com.webber.jogging.security.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jogging/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(path = "password", produces = "application/json")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        try {
            userService.changePassword(user, passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
            LOG.info("Changed password for user " + user.getUsername());
            return ResponseEntity.ok(new ChangePasswordResponse(false, "Password change successful"));
        } catch (SecurityException e) {
            LOG.warn("Couldn't change password", e);
            return ResponseEntity.status(403).body(new ChangePasswordResponse(true, "Old password incorrect"));
        }
    }

}
