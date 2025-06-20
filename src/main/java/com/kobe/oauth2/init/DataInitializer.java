package com.kobe.oauth2.init;

import com.kobe.oauth2.domain.Article;
import com.kobe.oauth2.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Profile({"local", "dev"}) // 이 프로파일에서만 실행됨.
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

	private final BlogRepository blogRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (blogRepository.count() == 0) {
			// 데이터가 하나도 없을 때만 실행
			/**
			 * blogRepository.count()는 데이터베이스의 article 테이블에 저장된 레코드(행)의 개수를 반환합니다.
			 * 즉, == 0은 테이블에 저장된 데이터가 하나도 없다는 것을 의미합니다.
			 */
			blogRepository.save(new Article("user1", "제목 1", "내용 1", LocalDateTime.now(), LocalDateTime.now()));
			blogRepository.save(new Article("user2", "제목 2", "내용 2", LocalDateTime.now(), LocalDateTime.now()));
			blogRepository.save(new Article("user3", "제목 3", "내용 3", LocalDateTime.now(), LocalDateTime.now()));
		}
	}
}
