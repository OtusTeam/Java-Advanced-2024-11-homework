package org.example.task18.persistence.mappers;

import org.example.task18.models.UserReq;
import org.example.task18.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserReq userReq);
    UserReq entityToDto(User user);
}
