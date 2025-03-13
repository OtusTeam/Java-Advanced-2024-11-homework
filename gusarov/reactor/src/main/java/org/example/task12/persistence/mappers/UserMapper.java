package org.example.task12.persistence.mappers;

import org.example.task12.models.UserReq;
import org.example.task12.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserReq userReq);
    UserReq entityToDto(User user);
}
