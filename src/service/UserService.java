package service;

import model.User;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
	public User findUserByEmail(String email);
	public Integer updateUser(User user);

}
