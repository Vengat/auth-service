package com.vengat.tuts.service;

import java.util.Optional;

import com.vengat.tuts.model.DashboardUserDetails;
import com.vengat.tuts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vengat.tuts.model.User;

@Service
public class DashboardUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// return new User("foo", "foo", new ArrayList<>());
		// return new DashboardUserDetails(username);
		Optional<User> user = userRepository.findByUserName(userName);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found " + userName));
		return user.map(DashboardUserDetails::new).get();
	}

}
