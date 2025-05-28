package edu.janeforjane.provider.internal;

import edu.janeforjane.provider.internal.entities.FairytaleCharacter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
public class FairytaleCharactersStorage {

    //name+obj
    private static final Map<String, FairytaleCharacter> fairytaleCharactersMap = new HashMap<>();

    public static void fillData(List<FairytaleCharacter> fairytaleCharacters){
        fairytaleCharacters.forEach(fairytaleCharacter -> fairytaleCharactersMap.put(fairytaleCharacter.getName(), fairytaleCharacter));
        log.info("FairytaleCharacters data is ready! There are {} characters", fairytaleCharacters.size());
    }

    public Optional<FairytaleCharacter> findByName(String name){
        return fairytaleCharactersMap.entrySet().stream()
                .filter(character -> Objects.equals(character.getKey(), name))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public void save(FairytaleCharacter fairytaleCharacter){
        fairytaleCharactersMap.put(fairytaleCharacter.getName(), fairytaleCharacter);
    }

    public List<FairytaleCharacter> findAll(){
        return fairytaleCharactersMap.values().stream().toList();
    }
}
