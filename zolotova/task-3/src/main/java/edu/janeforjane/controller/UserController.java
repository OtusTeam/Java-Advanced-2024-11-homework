package edu.janeforjane.controller;

import edu.janeforjane.entities.User;
import edu.janeforjane.exceptions.LoginAlreadyExistsException;
import edu.janeforjane.exceptions.UnableSaveDataException;
import edu.janeforjane.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

//    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String login, @RequestParam String password) {
        log.info("Got request for registration");
        try {
            userService.registerUser(login, password);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully!");
        } catch (LoginAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Such login already exists");
        } catch (UnableSaveDataException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Some problems with saving");
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
