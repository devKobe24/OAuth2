//package com.kobe.oauth2.util;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//public class CookieUtil {
//	// 요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
//	public static void addCookie(HttpServletResponse response,
//	                             String name,
//	                             String value,
//	                             int maxAge) {
//		Cookie cookie = new Cookie(name, value);
//		cookie.setPath("/");
//		cookie.setMaxAge(maxAge);
//		response.addCookie(cookie);
//	}
//
//	// 쿠키의 이름을 입력 받아 쿠키 삭제
//	public static void deleteCookie(HttpServletRequest request,
//	                                HttpServletResponse response,
//	                                String name) {
//		Cookie[] cookies = request.getCookies();
//		if (cookies == null) {
//			return;
//		}
//
//		for (Cookie cookie : cookies) {
//			if (name.equals(cookie.getName())) {
//				cookie.setValue("");
//				cookie.setPath("/");
//				cookie.setMaxAge(0);
//				response.addCookie(cookie);
//			}
//		}
//	}
//
//	// 객체를 직렬화해 쿠키의 값으로 변환
//	public static String serialize(Object obj) {
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			String json = mapper.writeValueAsString(obj);
//			return Base64.getUrlEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
//		} catch (JsonProcessingException e) {
//			throw new RuntimeException("직렬화 실패", e);
//		}
//	}
//
//	// 쿠키를 역직렬화해 객체로 변환
//	public static <T> T deserialize(Cookie cookie, Class<T> cls) {
//		try {
//			String json = new String(Base64.getUrlDecoder().decode(cookie.getValue()), StandardCharsets.UTF_8);
//			ObjectMapper objectMapper = new ObjectMapper();
//			return objectMapper.readValue(json, cls);
//		} catch (IOException e) {
//			throw new RuntimeException("역직렬화 실패", e);
//		}
//	}
//}
