package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.provider.ProviderService;
import ru.otus.entity.User;
import ru.otus.model.UserDto;
import ru.otus.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository repository;
    private final ProviderService service;
    private final ModelMapper mapper;

    @Override
    public String register(UserDto user) {
        return repository.save(mapper.map(user, User.class))
                .getId();
    }

    @Override
    public UserDto getUserById(String userId) {
        return service.findById(userId);
    }
}
