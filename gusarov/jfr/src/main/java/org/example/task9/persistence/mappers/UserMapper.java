package org.example.task9.persistence.mappers;

import org.example.task9.models.UserReq;
import org.example.task9.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserReq userReq);
    UserReq entityToDto(User user);
}
