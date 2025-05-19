package org.example.task3.persistence.mappers;

import org.example.task3.models.UserReq;
import org.example.task3.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserReq userReq);
    UserReq entityToDto(User user);
}
