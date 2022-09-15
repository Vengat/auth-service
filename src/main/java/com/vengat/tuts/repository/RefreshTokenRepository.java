package com.vengat.tuts.repository;

import java.util.Optional;

import com.vengat.tuts.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.vengat.tuts.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	@Override
	Optional<RefreshToken> findById(Long id);

	Optional<RefreshToken> findByToken(String token);

	@Modifying
	int deleteByUser(User user);
}
