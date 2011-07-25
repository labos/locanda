/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
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
	
	

	@Override
	public Integer insertUser(User user) {
		
		return this.getUserMapper().insertUser(user);
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}	

}
