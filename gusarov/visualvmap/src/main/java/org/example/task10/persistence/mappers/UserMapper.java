package org.example.task10.persistence.mappers;

import org.example.task10.models.UserReq;
import org.example.task10.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserReq userReq);
    UserReq entityToDto(User user);
}
