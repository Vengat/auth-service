package com.vengat.tuts.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vengat.tuts.model.DashboardUserDetails;
import com.vengat.tuts.model.ERole;
import com.vengat.tuts.model.JwtResponse;
import com.vengat.tuts.model.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vengat.tuts.util.JwtUtil;
import com.vengat.tuts.model.User;
import com.vengat.tuts.model.Role;
import com.vengat.tuts.exception.UserExistsException;
import com.vengat.tuts.model.MessageResponse;
import com.vengat.tuts.repository.RoleRepository;
import com.vengat.tuts.repository.UserRepository;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	DashboardUserDetailsService dashboardUserDetailsService;

	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtil;

	@Transactional
	public JwtResponse authenticateUser(String userName, String password) throws Exception {

		Authentication authentication = null;

		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		} catch (BadCredentialsException e) {
			logger.warn("authenticateUser: Exception occured " + e.getMessage());
			throw new Exception("Incorrect username or password", e);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		// String jwt = jwtUtil.generateJwtToken(authentication);
		final UserDetails userDetails = dashboardUserDetailsService.loadUserByUsername(userName);
		final String jwt = jwtUtil.generateToken(userDetails);
		DashboardUserDetails userDetailsRes = (DashboardUserDetails) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		RefreshToken refreshToken = refreshTokenService.generateRefreshToken(userDetailsRes.getId());

		return new JwtResponse(jwt, refreshToken.getToken(), userDetailsRes.getId(), userDetailsRes.getUsername(),
				userDetailsRes.getEmail(), roles);
	}

	@Transactional
	public MessageResponse registerUser(String userName, String password, String email, Set<String> strRoles)
			throws UserExistsException {

		if (userRepository.existsByUserName(userName)) {
			throw new UserExistsException("Username taken");
		}

		if (userRepository.existsByEmail(email)) {
			throw new UserExistsException("User email taken");
		}

		User user = new User(userName, email, encoder.encode(password));

		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return new MessageResponse("User registered successfully!");

	}

}
