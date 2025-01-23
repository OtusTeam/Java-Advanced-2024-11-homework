package org.example.task4.persistence.mappers;

import org.example.task4.models.UserReq;
import org.example.task4.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target="login", source = "login")
    @Mapping(target="password", source = "password")
    User dtoToEntity(UserReq userReq);
    UserReq entityToDto(User user);
}
