package ru.otus.provider;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.model.UserDto;
import ru.otus.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    @Override
    public UserDto findById(String userId) {
        return repository.findById(userId)
                .map(user -> mapper.map(user, UserDto.class))
                .orElseThrow(IllegalStateException::new);
    }
}
