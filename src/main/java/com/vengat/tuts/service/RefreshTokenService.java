package com.vengat.tuts.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.vengat.tuts.model.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vengat.tuts.exception.TokenRefreshException;
import com.vengat.tuts.repository.RefreshTokenRepository;
import com.vengat.tuts.repository.UserRepository;

@Service
public class RefreshTokenService {

	@Value("${auth.vengat.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMS;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken generateRefreshToken(Long userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMS));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken refreshToken) throws TokenRefreshException {
		if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(refreshToken);
			throw new TokenRefreshException(refreshToken.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return refreshToken;
	}

	@Transactional
	public int deleteByUserId(Long userId) {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}
}
