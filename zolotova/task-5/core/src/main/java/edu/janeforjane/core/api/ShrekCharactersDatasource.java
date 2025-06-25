package edu.janeforjane.core.api;

import edu.janeforjane.entities.CommonEnchantedCharacter;

import java.util.List;
import java.util.Optional;

public interface ShrekCharactersDatasource {

    Optional<CommonEnchantedCharacter> getByName(String name);
    List<CommonEnchantedCharacter> findAll();
    void save(CommonEnchantedCharacter commonEnchantedCharacter);
}

