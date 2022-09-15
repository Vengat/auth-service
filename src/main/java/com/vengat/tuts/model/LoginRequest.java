package com.vengat.tuts.model;

import lombok.AllArgsConstructor;

//import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	// @NotBlank
	private @NonNull String username;

	// @NotBlank
	private @NonNull String password;

}
