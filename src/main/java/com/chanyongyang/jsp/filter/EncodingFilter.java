package com.chanyongyang.jsp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


//인코딩 방법 2 Filter 사용
@WebFilter("/*") // *는 모든 요청에 이 필터를 모두 씌우겠다는 의미 따로 추가작업을 해줄 필요가 없음
public class EncodingFilter implements Filter {

	// doFilter(필수)는 있어야함.
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) // service역할과 같음
			throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
	}

	
}
