package com.vengat.tuts.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vengat.tuts.exception.TokenRefreshException;
import com.vengat.tuts.exception.UserExistsException;
import com.vengat.tuts.model.JwtResponse;
import com.vengat.tuts.model.LoginRequest;
import com.vengat.tuts.model.MessageResponse;
import com.vengat.tuts.model.RefreshToken;
import com.vengat.tuts.model.SignupRequest;
import com.vengat.tuts.model.LogOutRequest;
import com.vengat.tuts.model.TokenRefreshRequest;
import com.vengat.tuts.model.TokenRefreshResponse;
import com.vengat.tuts.service.AuthService;
import com.vengat.tuts.service.UserService;
import com.vengat.tuts.util.JwtUtil;
import com.vengat.tuts.service.RefreshTokenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	AuthService authService;

	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

		JwtResponse jwtResponse = null;
		try {
			jwtResponse = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
		} catch (Exception e) {
			// logger.warn("authenticateUser controller Invalid credentials ", e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid credentials"));
		}

		return ResponseEntity.ok(jwtResponse);

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws UserExistsException {

		String userName = signUpRequest.getUsername();
		String password = signUpRequest.getPassword();
		String email = signUpRequest.getEmail();
		Set<String> strRoles = signUpRequest.getRole();

		MessageResponse messageResponse = null;

		try {
			messageResponse = authService.registerUser(userName, password, email, strRoles);
		} catch (UserExistsException e) {
			// logger.warn("registerUser controller: user exists", e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User exists " + e.getMessage()));
		}

		return ResponseEntity.ok(messageResponse);
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest)
			throws TokenRefreshException {

		String refreshToken = tokenRefreshRequest.getRefreshToken();

		// TODO: Delete these following lines of code if its annoying. This is to
		// explain what the functional programming code does
		// RefreshToken token = refreshTokenService.findByToken(refreshToken).get();
		// token = refreshTokenService.verifyExpiration(token);
		// String userName = token.getUser().getUserName();
		// String accessToken = jwtUtil.generateJwtTokenFromUsername(userName);
		// return ResponseEntity.ok(new TokenRefreshResponse(accessToken,
		// refreshToken));

		return refreshTokenService.findByToken(refreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtil.generateJwtTokenFromUsername(user.getUserName());
					return ResponseEntity.ok(new TokenRefreshResponse(token, refreshToken));
				}).orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
		refreshTokenService.deleteByUserId(logOutRequest.getUserId());
		return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	}
}
