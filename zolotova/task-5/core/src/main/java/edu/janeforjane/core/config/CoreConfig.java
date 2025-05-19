package edu.janeforjane.core.config;

import edu.janeforjane.core.api.ShrekCharactersDatasource;
import edu.janeforjane.core.internal.ShrekCharactersDatasourceImpl;
import edu.janeforjane.core.internal.ShrekCharactersStorage;
import edu.janeforjane.core.internal.CoreDataLoaderListener;
import edu.janeforjane.core.internal.mapper.ShrekCharacterEnchantedCharacterMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public ShrekCharactersStorage shrekCharactersStorage() {
        return new ShrekCharactersStorage();
    }

    @Bean
    public ShrekCharacterEnchantedCharacterMapper shrekCharacterMapper() {
        return Mappers.getMapper(ShrekCharacterEnchantedCharacterMapper.class);
    }

    @Bean
    public ShrekCharactersDatasource shrekCharactersDatasource(
            ShrekCharactersStorage storage,
            ShrekCharacterEnchantedCharacterMapper mapper
    ) {
        return new ShrekCharactersDatasourceImpl(mapper, storage);
    }

    @Bean
    public CoreDataLoaderListener coreDataLoaderListener() {
        return new CoreDataLoaderListener();
    }




}
