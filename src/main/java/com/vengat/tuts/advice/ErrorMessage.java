package com.vengat.tuts.advice;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;

}
