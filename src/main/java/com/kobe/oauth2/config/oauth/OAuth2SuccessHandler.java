package com.kobe.oauth2.config.oauth;

import com.kobe.oauth2.config.jwt.TokenProvider;
import com.kobe.oauth2.domain.RefreshToken;
import com.kobe.oauth2.domain.User;
import com.kobe.oauth2.repository.RefreshTokenRepository;
import com.kobe.oauth2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
	private static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
	private static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(30);
	private static final String REDIRECT_PATH = "/articles";

	private final TokenProvider tokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserService userService;

	private void saveRefreshToken(Long userId, String refreshToken) {
		RefreshToken tokenEntity = refreshTokenRepository.findByUserId(userId)
			.map(entity -> entity.update(refreshToken))
			.orElse(new RefreshToken(userId, refreshToken));
		refreshTokenRepository.save(tokenEntity);
	}

	private void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
		ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
			.path("/")
			.httpOnly(true)
			.maxAge(REFRESH_TOKEN_DURATION)
			.secure(true)
			.sameSite("Lax")
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	private String getTargetUrl(String accessToken) {
		return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
			.queryParam("token", accessToken)
			.build()
			.toUriString();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		String email = (String) oAuth2User.getAttributes().get("email");
		User user = userService.findByEmail(email);

		String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
		saveRefreshToken(user.getId(), refreshToken);
		addRefreshTokenToCookie(response, refreshToken);

		String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
		String targetUrl = getTargetUrl(accessToken);

		clearAuthenticationAttributes(request);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
