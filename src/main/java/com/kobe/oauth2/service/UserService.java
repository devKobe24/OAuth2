package com.kobe.oauth2.service;

import com.kobe.oauth2.domain.User;
import com.kobe.oauth2.dto.request.AddUserRequest;
import com.kobe.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public Long save(AddUserRequest dto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return userRepository.save(User.builder()
			.email(dto.getEmail())
			.password(encoder.encode(dto.getPassword()))
			.build()).getId();
	}

	public User findById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}
}
