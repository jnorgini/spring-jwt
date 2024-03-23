package com.norgini.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.norgini.dtos.UserResponse;
import com.norgini.entities.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserResponseAssembler {

	private ModelMapper mapper;

	public UserResponse toModel(User user) {
		return mapper.map(user, UserResponse.class);
	}

	public List<UserResponse> toCollectModel(List<User> users) {
		return users.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
