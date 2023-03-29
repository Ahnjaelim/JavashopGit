package kr.co.javashop.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import kr.co.javashop.security.CustomUserDetailsService;
import kr.co.javashop.security.handler.Custom403Handler;
import kr.co.javashop.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // 어노테이션으로 권한 설정 => 컨트롤러에서 @PreAuthorize 사용 가능
public class CustomSecurityConfig {

	private final DataSource dataSource;
	private final CustomUserDetailsService customUserDetailsService;
	
	// 비로그인 접근 설정
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	
		log.info("----------configure----------");
		// http.formLogin(); 시큐리티 기본 폼 
		http.formLogin().loginPage("/member/login"); // 커스텀 시큐리티 폼
		http.csrf().disable(); // CSRF토큰 임시 비활성화
		// => 희한하게도 POST작업을 따로 하지 않아도 로그인됨
		
		http.rememberMe()
			.key("12345678")
			.tokenRepository(persistentTokenRepository())
			.userDetailsService(customUserDetailsService)
			.tokenValiditySeconds(60*60*24*30);
		
		// 403에러 커스텀 페이지 처리
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		
		// 카카오 연동
		http.oauth2Login().loginPage("/member/login").successHandler(authenticationSuccessHandler());
			
		return http.build();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}	
	
	// 정적 자원 파일들을 필터에서 제외
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		log.info("----------web configure----------");
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	// 패스워드 암호화
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 403에러 커스텀 페이지 처리
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new Custom403Handler();
	}
	
	// 카카오 로그인 성공 핸들러
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomSocialLoginSuccessHandler(passwordEncoder());
	}
}
