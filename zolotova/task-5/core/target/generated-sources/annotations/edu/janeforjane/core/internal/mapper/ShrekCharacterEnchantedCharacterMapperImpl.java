package edu.janeforjane.core.internal.mapper;

import edu.janeforjane.core.internal.entities.ShrekCharacterDB;
import edu.janeforjane.entities.CommonEnchantedCharacter;
import java.util.ArrayList;
import java.util.List;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-14T23:16:53+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Ubuntu)"
)
*/
public class ShrekCharacterEnchantedCharacterMapperImpl extends ShrekCharacterEnchantedCharacterMapper {

    @Override
    public CommonEnchantedCharacter mapDB(ShrekCharacterDB shrekCharacterDB) {
        if ( shrekCharacterDB == null ) {
            return null;
        }

        CommonEnchantedCharacter commonEnchantedCharacter = new CommonEnchantedCharacter();

        commonEnchantedCharacter.setId( shrekCharacterDB.getId() );
        commonEnchantedCharacter.setName( shrekCharacterDB.getName() );
        commonEnchantedCharacter.setShrekRole( shrekCharacterDB.getShrekRole() );
        commonEnchantedCharacter.setPersonalityTraits( shrekCharacterDB.getPersonalityTraits() );
        commonEnchantedCharacter.setShrekStoryline( shrekCharacterDB.getShrekStoryline() );
        commonEnchantedCharacter.setGroup( shrekCharacterDB.getGroup() );
        commonEnchantedCharacter.setAlignment( shrekCharacterDB.getAlignment() );
        commonEnchantedCharacter.setOriginStory( shrekCharacterDB.getOriginStory() );
        List<String> list = shrekCharacterDB.getSpecialAbilities();
        if ( list != null ) {
            commonEnchantedCharacter.setSpecialAbilities( new ArrayList<String>( list ) );
        }

        return commonEnchantedCharacter;
    }

    @Override
    public List<CommonEnchantedCharacter> mapDB(List<ShrekCharacterDB> shrekCharacterDBS) {
        if ( shrekCharacterDBS == null ) {
            return null;
        }

        List<CommonEnchantedCharacter> list = new ArrayList<CommonEnchantedCharacter>( shrekCharacterDBS.size() );
        for ( ShrekCharacterDB shrekCharacterDB : shrekCharacterDBS ) {
            list.add( mapDB( shrekCharacterDB ) );
        }

        return list;
    }

    @Override
    public ShrekCharacterDB map(CommonEnchantedCharacter commonEnchantedCharacter) {
        if ( commonEnchantedCharacter == null ) {
            return null;
        }

        ShrekCharacterDB shrekCharacterDB = new ShrekCharacterDB();

        shrekCharacterDB.setId( commonEnchantedCharacter.getId() );
        shrekCharacterDB.setName( commonEnchantedCharacter.getName() );
        shrekCharacterDB.setShrekRole( commonEnchantedCharacter.getShrekRole() );
        shrekCharacterDB.setPersonalityTraits( commonEnchantedCharacter.getPersonalityTraits() );
        shrekCharacterDB.setShrekStoryline( commonEnchantedCharacter.getShrekStoryline() );
        shrekCharacterDB.setGroup( commonEnchantedCharacter.getGroup() );
        shrekCharacterDB.setAlignment( commonEnchantedCharacter.getAlignment() );
        shrekCharacterDB.setOriginStory( commonEnchantedCharacter.getOriginStory() );
        List<String> list = commonEnchantedCharacter.getSpecialAbilities();
        if ( list != null ) {
            shrekCharacterDB.setSpecialAbilities( new ArrayList<String>( list ) );
        }

        return shrekCharacterDB;
    }

    @Override
    public List<ShrekCharacterDB> map(List<CommonEnchantedCharacter> commonEnchantedCharacters) {
        if ( commonEnchantedCharacters == null ) {
            return null;
        }

        List<ShrekCharacterDB> list = new ArrayList<ShrekCharacterDB>( commonEnchantedCharacters.size() );
        for ( CommonEnchantedCharacter commonEnchantedCharacter : commonEnchantedCharacters ) {
            list.add( map( commonEnchantedCharacter ) );
        }

        return list;
    }
}
