package persistence.mybatis.mappers;

import model.User;

public interface UserMapper {
	public User findUserByEmail(String email);

}
