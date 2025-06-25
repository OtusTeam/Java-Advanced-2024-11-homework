package edu.janeforjane.core.internal.mapper;

import edu.janeforjane.core.internal.entities.ShrekCharacterDB;
import edu.janeforjane.entities.CommonEnchantedCharacter;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public abstract class ShrekCharacterEnchantedCharacterMapper {

    public abstract CommonEnchantedCharacter mapDB(ShrekCharacterDB shrekCharacterDB);

    public abstract List<CommonEnchantedCharacter> mapDB(List<ShrekCharacterDB> shrekCharacterDBS);

    public abstract ShrekCharacterDB map(CommonEnchantedCharacter commonEnchantedCharacter);

    public abstract List<ShrekCharacterDB> map(List<CommonEnchantedCharacter> commonEnchantedCharacters);


}
