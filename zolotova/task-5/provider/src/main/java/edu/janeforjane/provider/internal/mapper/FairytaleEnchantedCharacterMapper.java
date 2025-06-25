package edu.janeforjane.provider.internal.mapper;

import edu.janeforjane.entities.CommonEnchantedCharacter;
import edu.janeforjane.provider.internal.entities.FairytaleCharacter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class FairytaleEnchantedCharacterMapper {

    public abstract CommonEnchantedCharacter mapDB(FairytaleCharacter fairytaleCharacter);

    public abstract List<CommonEnchantedCharacter> mapDB(List<FairytaleCharacter> fairytaleCharacters);

    public abstract FairytaleCharacter map(CommonEnchantedCharacter commonEnchantedCharacter);

    public abstract List<FairytaleCharacter> map(List<CommonEnchantedCharacter> commonEnchantedCharacters);
}
