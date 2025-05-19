package edu.janeforjane.service.config;

import edu.janeforjane.core.api.ShrekCharactersDatasource;
import edu.janeforjane.provider.api.FairytaleCharactersProvider;
import edu.janeforjane.service.api.NewCharactersService;
import edu.janeforjane.service.internal.MagicalIdGenerator;
import edu.janeforjane.service.internal.NewCharactersServiceImpl;
import edu.janeforjane.service.internal.ShrekRoleAssignmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewCharacterServiceSpringConfig {

    @Bean
    public NewCharactersService newCharactersService(
            ShrekCharactersDatasource shrekCharactersDatasource,
            FairytaleCharactersProvider fairytaleCharactersProvider,
            MagicalIdGenerator idGenerator,
            ShrekRoleAssignmentService assignmentService
    ){
        return new NewCharactersServiceImpl(shrekCharactersDatasource, fairytaleCharactersProvider, idGenerator, assignmentService);
    }
}
