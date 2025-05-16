package edu.janeforjane.provider.internal.mapper;

import edu.janeforjane.entities.CommonEnchantedCharacter;
import edu.janeforjane.provider.internal.entities.FairytaleCharacter;
import java.util.ArrayList;
import java.util.List;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-14T23:16:53+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Ubuntu)"
)
*/
public class FairytaleEnchantedCharacterMapperImpl extends FairytaleEnchantedCharacterMapper {

    @Override
    public CommonEnchantedCharacter mapDB(FairytaleCharacter fairytaleCharacter) {
        if ( fairytaleCharacter == null ) {
            return null;
        }

        CommonEnchantedCharacter commonEnchantedCharacter = new CommonEnchantedCharacter();

        commonEnchantedCharacter.setName( fairytaleCharacter.getName() );
        commonEnchantedCharacter.setGroup( fairytaleCharacter.getGroup() );
        commonEnchantedCharacter.setAlignment( fairytaleCharacter.getAlignment() );
        commonEnchantedCharacter.setOriginStory( fairytaleCharacter.getOriginStory() );
        List<String> list = fairytaleCharacter.getSpecialAbilities();
        if ( list != null ) {
            commonEnchantedCharacter.setSpecialAbilities( new ArrayList<String>( list ) );
        }

        return commonEnchantedCharacter;
    }

    @Override
    public List<CommonEnchantedCharacter> mapDB(List<FairytaleCharacter> fairytaleCharacters) {
        if ( fairytaleCharacters == null ) {
            return null;
        }

        List<CommonEnchantedCharacter> list = new ArrayList<CommonEnchantedCharacter>( fairytaleCharacters.size() );
        for ( FairytaleCharacter fairytaleCharacter : fairytaleCharacters ) {
            list.add( mapDB( fairytaleCharacter ) );
        }

        return list;
    }

    @Override
    public FairytaleCharacter map(CommonEnchantedCharacter commonEnchantedCharacter) {
        if ( commonEnchantedCharacter == null ) {
            return null;
        }

        FairytaleCharacter fairytaleCharacter = new FairytaleCharacter();

        fairytaleCharacter.setName( commonEnchantedCharacter.getName() );
        fairytaleCharacter.setGroup( commonEnchantedCharacter.getGroup() );
        fairytaleCharacter.setAlignment( commonEnchantedCharacter.getAlignment() );
        fairytaleCharacter.setOriginStory( commonEnchantedCharacter.getOriginStory() );
        List<String> list = commonEnchantedCharacter.getSpecialAbilities();
        if ( list != null ) {
            fairytaleCharacter.setSpecialAbilities( new ArrayList<String>( list ) );
        }

        return fairytaleCharacter;
    }

    @Override
    public List<FairytaleCharacter> map(List<CommonEnchantedCharacter> commonEnchantedCharacters) {
        if ( commonEnchantedCharacters == null ) {
            return null;
        }

        List<FairytaleCharacter> list = new ArrayList<FairytaleCharacter>( commonEnchantedCharacters.size() );
        for ( CommonEnchantedCharacter commonEnchantedCharacter : commonEnchantedCharacters ) {
            list.add( map( commonEnchantedCharacter ) );
        }

        return list;
    }
}
