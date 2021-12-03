package com.olx.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.User;
import com.olx.entity.UserEntity;
import com.olx.repository.UserRepository;
import com.olx.security.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	private UserRepository userRepository;
	

	
	@Autowired
	ModelMapper modelMapper;
	
	public User createNewUser(User user) {
		        log.info("Entered into creating a new user service ...");
				UserEntity userEntity = this.modelMapper.map(user, UserEntity.class);
				userEntity.setActive(true);
				userEntity.setRoles("USER");
				userEntity = this.userRepository.save(userEntity);
				// Converting the Stock entity back into the DTO object
				User userDto = this.modelMapper.map(userEntity, User.class);
				log.info("Created a new user");
				return userDto;
	}
	
	
	@Override
	public User getUser(String authToken) {	
		log.info("Entered into the service of getting user by auth token");
		String strArr[] = authToken.split(" ");
		String token = strArr[1];
		System.out.println(token);
		String username =new  JwtUtil().extractUsername(token);
		log.info("Extracting the username");
		System.out.println(username);
		log.info("Successfully authenticated !!! ");
		UserEntity userEntity = userRepository.findBySingleUsername(username);
		
		//List<User> userDtoList = new ArrayList<User>();
		// userDtoList.add(null)
	    //	for(UserEntity userEntity: userEntityList) {
        //	userDtoList.add(userDto);
    	//	}
		
		User userDto = this.modelMapper.map(userEntity, User.class);
		log.info("Successfully fetched the user record");
		return userDto;
	
	}

	
	@Override
	public boolean LogoutUserByAuthtoken(String authToken) {
		return false;
	}
	
	
/*	
	@Override
	public List<User> getAllUser() {
		List<UserEntity> userEntities = userRepository.findAll();
		List<User> userDtoList = new ArrayList<User>();
		for(UserEntity userEntity: userEntities) {
			User userDto = new User();
			userDto.setFirstname(userEntity.getFirstname());
			userDto.setLastname(userEntity.getLastname());
			userDtoList.add(userDto);
		}
		return userDtoList;
	}
*/	
	
}
