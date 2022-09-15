package com.vengat.tuts.repository;

import java.util.Optional;

import com.vengat.tuts.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vengat.tuts.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);

	Optional<Role> findRoleById(Long id);
}
