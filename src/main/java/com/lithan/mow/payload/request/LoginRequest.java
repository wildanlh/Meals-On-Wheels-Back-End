package com.lithan.mow.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest {

	@Email
	private String email;

	@Size(min = 6)
	private String password;
}
