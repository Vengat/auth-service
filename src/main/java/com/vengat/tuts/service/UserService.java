package com.vengat.tuts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vengat.tuts.exception.UserExistsException;
import com.vengat.tuts.model.User;
import com.vengat.tuts.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public User create(User user) throws UserExistsException {
		String userName = user.getUserName();
		try {
			if (isUserExists(userName)) {
				throw new UserExistsException("User exists " + userName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repository.save(user);
	}

	public boolean isUserExists(String userName) throws Exception {
		if (repository.findByUserName(userName).isEmpty()) {
			return false;
		}
		return true;
	}

}
