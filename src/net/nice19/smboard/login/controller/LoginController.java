package net.nice19.smboard.login.controller;

import javax.servlet.http.HttpSession;

import net.nice19.smboard.login.model.LoginSessionModel;
import net.nice19.smboard.login.service.LoginService;
import net.nice19.smboard.login.service.LoginValidator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	private ApplicationContext context;
	
	@RequestMapping("/login.do")
	public String login() {
		return "/board/login";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public ModelAndView loginProc(@ModelAttribute("LoginModel") LoginSessionModel loginModel, BindingResult result, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		// form validation
		new LoginValidator().validate(loginModel,  result);;
		if(result.hasErrors()) {
			mav.setViewName("/board/login");
			return mav;
		}
		
		String userId = loginModel.getUserId();
		String userPw = loginModel.getUserPw();
		
		context = new ClassPathXmlApplicationContext("/config/applicationContext.xml");
		LoginService loginService = (LoginService) context.getBean("loginService");
		LoginSessionModel loginCheckResult = loginService.checkUserId(userId);
		
		// check joined user
		if(loginCheckResult == null) {
			mav.addObject("userId", userId);
			mav.addObject("errCode", 1);			// DB 에 해당 유저가 없는 경우
			mav.setViewName("/board/login");
			return mav;
		}else {
			mav.addObject("userId", userId);
			mav.addObject("errCode", 2);			// 비번 오류
			mav.setViewName("/board/login");
			return mav;
		}
	}
	
	@RequestMapping("/logoug.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login.do";
	}
}
