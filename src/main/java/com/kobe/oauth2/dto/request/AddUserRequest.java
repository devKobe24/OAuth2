package com.kobe.oauth2.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequest {
	private String email;
	private String password;
}
