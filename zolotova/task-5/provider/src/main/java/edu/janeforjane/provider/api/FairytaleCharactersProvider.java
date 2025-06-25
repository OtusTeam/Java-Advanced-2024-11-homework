package edu.janeforjane.provider.api;

import edu.janeforjane.entities.CommonEnchantedCharacter;

import java.util.List;
import java.util.Optional;

public interface FairytaleCharactersProvider {

    Optional<CommonEnchantedCharacter> findByName(String login);

    void save(CommonEnchantedCharacter inputUser);

    List<CommonEnchantedCharacter> findAll();
}
