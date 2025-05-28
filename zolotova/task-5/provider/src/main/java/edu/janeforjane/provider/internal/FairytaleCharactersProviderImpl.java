package edu.janeforjane.provider.internal;

import edu.janeforjane.entities.CommonEnchantedCharacter;
import edu.janeforjane.provider.api.FairytaleCharactersProvider;
import edu.janeforjane.provider.internal.mapper.FairytaleEnchantedCharacterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class FairytaleCharactersProviderImpl implements FairytaleCharactersProvider {

//    private static final Logger log = LoggerFactory.getLogger(FairytaleCharactersProviderImpl.class);

    private FairytaleCharactersStorage storage;
    private FairytaleEnchantedCharacterMapper mapper;

    @Override
    public Optional<CommonEnchantedCharacter> findByName(String name) {

        return storage.findByName(name)
                .map(mapper::mapDB)
                .or(Optional::empty);
    }

    @Override
    public void save(CommonEnchantedCharacter inputUser) {
        storage.save(mapper.map(inputUser));
    }

    @Override
    public List<CommonEnchantedCharacter> findAll() {
        return mapper.mapDB(storage.findAll());
    }
}
