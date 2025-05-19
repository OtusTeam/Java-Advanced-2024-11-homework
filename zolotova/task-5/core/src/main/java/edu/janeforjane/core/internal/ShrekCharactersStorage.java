package edu.janeforjane.core.internal;

import edu.janeforjane.core.internal.entities.ShrekCharacterDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
public class ShrekCharactersStorage {

    //id+obj
    private static final Map<String, ShrekCharacterDB> shrekCharacterMap = new HashMap<>();

    public static void fillData(List<ShrekCharacterDB> shrekCharacterDBS){
        shrekCharacterDBS.forEach(shrekCharacterDB -> shrekCharacterMap.put(shrekCharacterDB.getId(), shrekCharacterDB));
        log.info("ShrekCharacters data is ready! There are {} characters", shrekCharacterMap.entrySet().size());
    }

    public void create(ShrekCharacterDB shrekCharacterDB){
        shrekCharacterMap.put(shrekCharacterDB.getId(), shrekCharacterDB);
    }

    public Optional<ShrekCharacterDB> findByName(String name){
        return shrekCharacterMap.entrySet().stream()
                .filter(stringShrekCharacterDBEntry -> Objects.equals(stringShrekCharacterDBEntry.getValue().getName(), name))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    public List<ShrekCharacterDB> getAll(){
        return shrekCharacterMap.values().stream()
                .toList();
    }

}
