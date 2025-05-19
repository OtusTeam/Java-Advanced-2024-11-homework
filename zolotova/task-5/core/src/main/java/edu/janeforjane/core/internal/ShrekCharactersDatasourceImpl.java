package edu.janeforjane.core.internal;

import edu.janeforjane.core.api.ShrekCharactersDatasource;
import edu.janeforjane.core.internal.entities.ShrekCharacterDB;
import edu.janeforjane.core.internal.mapper.ShrekCharacterEnchantedCharacterMapper;
import edu.janeforjane.entities.CommonEnchantedCharacter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class ShrekCharactersDatasourceImpl implements ShrekCharactersDatasource {

    ShrekCharacterEnchantedCharacterMapper mapper;
    ShrekCharactersStorage storage;


    @Override
    public Optional<CommonEnchantedCharacter> getByName(String name) {
        return storage.findByName(name)
                .map(shrekCharacterDB -> mapper.mapDB(shrekCharacterDB))
                .or(Optional::empty);
    }

    @Override
    public List<CommonEnchantedCharacter> findAll() {
        return storage.getAll()
                .stream().map(shrekCharacterDB -> mapper.mapDB(shrekCharacterDB))
                .toList();
    }

    @Override
    public void save(CommonEnchantedCharacter commonEnchantedCharacter) {
        ShrekCharacterDB shrekCharacterDB = mapper.map(commonEnchantedCharacter);
        log.info("Convert in shrekCharacterDB: {}", shrekCharacterDB);

        if(isUnique(shrekCharacterDB)) storage.create(shrekCharacterDB);
    }

    private boolean isUnique(ShrekCharacterDB shrekCharacterDB){
        return storage.findByName(shrekCharacterDB.getName()).isEmpty();
    }


}
