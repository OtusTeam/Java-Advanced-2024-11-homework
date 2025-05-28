package org.example.task16.persistence.mappers;

import org.example.task16.models.UserReq;
import org.example.task16.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserReq userReq);
    UserReq entityToDto(User user);
}
