package com.nwdy.phonevip.mapper;

import com.nwdy.phonevip.dto.request.RegisterRequest;
import com.nwdy.phonevip.dto.request.UserUpdateRequest;
import com.nwdy.phonevip.dto.response.UserResponse;
import com.nwdy.phonevip.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(RegisterRequest registerRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "password", ignore = true)
    void updateUser(UserUpdateRequest userUpdateRequest,@MappingTarget User user);
}
