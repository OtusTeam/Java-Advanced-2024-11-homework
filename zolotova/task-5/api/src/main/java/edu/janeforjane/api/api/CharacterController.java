package edu.janeforjane.api.api;

import edu.janeforjane.entities.CommonEnchantedCharacter;
import edu.janeforjane.service.api.NewCharactersService;
import edu.janeforjane.service.api.exceptions.CharacterAlreadyExistsException;
import edu.janeforjane.service.api.exceptions.NotEnchantedCharacterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class CharacterController {

    private static final Logger log = LoggerFactory.getLogger(CharacterController.class);

    @Autowired
    private NewCharactersService service;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String name) {
        log.info("Got request for registration new character");
        try {
            CommonEnchantedCharacter commonEnchantedCharacter = service.addNewCharacter(name);

            return ResponseEntity.status(HttpStatus.OK).body("Successfully!" + "\n" + commonEnchantedCharacter.magic_toString());
        } catch (NotEnchantedCharacterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("It's not enchanted character. Shrek is not a place for mediocre personages");
        } catch (CharacterAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Oops? this character already IN Shrek movies. It's time to rewatch all films!");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Some problems, sorry");
        }
    }

    @GetMapping("/shrekCharacters")
    public ResponseEntity<?> getAllShrekCharacters() {
        log.info("Adding");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAllShrekCharacters());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Some problems, sorry");
        }
    }

    @GetMapping("/enchantedCharacters")
    public ResponseEntity<?> getAllEnchantedCharacters() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAllEnchantedCharacters());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Some problems, sorry");
        }
    }


}
