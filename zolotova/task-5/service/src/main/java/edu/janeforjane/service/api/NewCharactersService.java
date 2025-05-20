package edu.janeforjane.service.api;

import edu.janeforjane.entities.CommonEnchantedCharacter;
import edu.janeforjane.service.api.exceptions.CharacterAlreadyExistsException;
import edu.janeforjane.service.api.exceptions.NotEnchantedCharacterException;

import java.util.List;

public interface NewCharactersService {

    CommonEnchantedCharacter addNewCharacter(String name) throws NotEnchantedCharacterException, CharacterAlreadyExistsException;

    List<CommonEnchantedCharacter> getAllShrekCharacters();
    List<CommonEnchantedCharacter> getAllEnchantedCharacters();
}
