package edu.janeforjane.provider.config;

import edu.janeforjane.provider.internal.FairytaleCharactersProviderImpl;
import edu.janeforjane.provider.internal.FairytaleCharactersStorage;
import edu.janeforjane.provider.internal.ProviderDataLoaderListener;
import edu.janeforjane.provider.internal.mapper.FairytaleEnchantedCharacterMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderConfig {

    @Bean
    public FairytaleCharactersStorage fairytaleCharactersStorage() {
        return new FairytaleCharactersStorage();
    }

    @Bean
    public FairytaleEnchantedCharacterMapper fairytaleEnchantedCharacterMapper() {
        return Mappers.getMapper(FairytaleEnchantedCharacterMapper.class);
    }

    @Bean
    public FairytaleCharactersProviderImpl fairytaleCharactersProvider(
            FairytaleCharactersStorage storage,
            FairytaleEnchantedCharacterMapper mapper
    ) {
        return new FairytaleCharactersProviderImpl(storage, mapper);
    }

    @Bean
    public ProviderDataLoaderListener providerDataLoaderListener() {
        return new ProviderDataLoaderListener();
    }




}
