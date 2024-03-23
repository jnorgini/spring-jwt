package com.norgini.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.norgini.dtos.UserRequest;
import com.norgini.entities.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserRequestDisassembler {

	private ModelMapper mapper;

	public User toDomainObject(UserRequest userRequest) {
		return mapper.map(userRequest, User.class);
	}

	public void copyToDomainObject(UserRequest userRequest, User user) {
		mapper.map(userRequest, user);
	}

}
