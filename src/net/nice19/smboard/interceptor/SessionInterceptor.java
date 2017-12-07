package net.nice19.smboard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// check variable
		Object userId = request.getSession().getAttribute("userId");
		//
		
		// login.do, join.do 실행시 걸리도록
		if(request.getRequestURI().equals("/SummerBoard/login.do") || request.getRequestURI().equals("/SummerBoard/member/join.do")){
			if(userId != null) {  // 로그인이 되어 있으면
				System.out.println("로그인 됨");
				response.sendRedirect(request.getContextPath() + "/board/list.do");
				return true;
			}else {
				System.out.println("로그인 안됨");
				return true;
			}
		}
		//
		
		// 다른 페이지인 경우
		if(userId == null) {  // 비 로그인시
			response.sendRedirect(request.getContextPath() + "/login.do");
			return false;
		}else {
			return true;
		}
		//
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
		
	}
}
