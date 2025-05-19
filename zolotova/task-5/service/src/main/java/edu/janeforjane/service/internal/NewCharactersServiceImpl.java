package edu.janeforjane.service.internal;

import edu.janeforjane.core.api.ShrekCharactersDatasource;
import edu.janeforjane.entities.CommonEnchantedCharacter;
import edu.janeforjane.provider.api.FairytaleCharactersProvider;
import edu.janeforjane.service.api.NewCharactersService;
import edu.janeforjane.service.api.exceptions.CharacterAlreadyExistsException;
import edu.janeforjane.service.api.exceptions.NotEnchantedCharacterException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class NewCharactersServiceImpl implements NewCharactersService {

    @NonNull
    private ShrekCharactersDatasource shrekCharactersDatasource;

    @NonNull
    private FairytaleCharactersProvider fairytaleCharactersProvider;

    @NonNull
    private MagicalIdGenerator idGenerator;

    @NonNull
    private ShrekRoleAssignmentService assignmentService;

    @Override
    public CommonEnchantedCharacter addNewCharacter(String name) throws NotEnchantedCharacterException, CharacterAlreadyExistsException {
        log.info("Got the new candidate for the role: {}", name);
        Optional<CommonEnchantedCharacter> fairytale_byname = fairytaleCharactersProvider.findByName(name);
        Optional<CommonEnchantedCharacter> shrek_byname = shrekCharactersDatasource.getByName(name);

        log.info("Let's check if {} is a shrek character", name);
        if(shrek_byname.isPresent()) {
            log.info("{} already exists in Shrek movie!", name);
            throw new CharacterAlreadyExistsException("Character already exists!");
        }
        log.info("No, {} isn't presented in Shrek", name);

        log.info("Let's check if {} is a fairytale character", name);
        if(fairytale_byname.isEmpty()) {
            log.info("{} is not enchanted character!", name);
            throw new NotEnchantedCharacterException("It's not enchanted character!");
        }
        log.info("Great, {} is an enchanted character!", name);

        CommonEnchantedCharacter character = fairytale_byname.get();
        log.info("Full info about {}: {}", name, character);

        String id = idGenerator.generateId();
        character.setId(id);
        log.info("{} has been assigned a magic id: {}", name, id);

        CommonEnchantedCharacter preparedCharacter = assignmentService.assignCharacter(character);
        shrekCharactersDatasource.save(preparedCharacter);

        return character;
    }

    @Override
    public List<CommonEnchantedCharacter> getAllShrekCharacters() {
        return shrekCharactersDatasource.findAll();
    }

    @Override
    public List<CommonEnchantedCharacter> getAllEnchantedCharacters() {
        return fairytaleCharactersProvider.findAll();
    }
}
