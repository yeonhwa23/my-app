package com.sp.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sp.app.interceptor.LoginCheckInterceptor;


@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {
	@Value("${file.upload-root}")
	private String uploadRoot;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 브라우저에서 /uploads/로 시작하는 요청이 오면 로컬의 uploadRoot 경로에서 파일을 찾도록 설정 
		registry.addResourceHandler("/uploads/**")
			.addResourceLocations("file:///" + uploadRoot);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 로그인 체크 인터셉터를 적용하지 않을 주소 설정
		List<String> excludePaths = new ArrayList<>();
		excludePaths.add("/");
		excludePaths.add("/dist/**");
		excludePaths.add("/member/login");
		excludePaths.add("/member/logout");
		excludePaths.add("/member/account");
		excludePaths.add("/member/userIdCheck");
		excludePaths.add("/member/complete");
		excludePaths.add("/member/pwdFind");
		excludePaths.add("/guest/main");
		excludePaths.add("/guest/list");
		excludePaths.add("/uploads/photo/**");
		excludePaths.add("/oauth/kakao/callback");
		
		// 인터셉터에 등록
		registry.addInterceptor(new LoginCheckInterceptor()).excludePathPatterns(excludePaths);
		
		/*
		registry.addInterceptor(new TimerCheckInterceptor())
			.addPathPatterns("/**")
			.order(1);

		registry.addInterceptor(new AuthCheckInterceptor())
			.addPathPatterns("/api/**")
			.excludePathPatterns("/api/login")
			.order(2);
		*/	
	}

}
