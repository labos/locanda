package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.UserMapper;

import model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper = null;
	
	
	public User findUserByEmail(String email) {
		
		return this.getUserMapper().findUserByEmail(email);
	}	
	
	public Integer updateUser(User user) {
		return this.getUserMapper().updateUser(user);
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}	

}
