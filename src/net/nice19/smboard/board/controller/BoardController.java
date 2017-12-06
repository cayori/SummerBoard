package net.nice19.smboard.board.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.nice19.smboard.board.model.BoardCommentModel;
import net.nice19.smboard.board.model.BoardModel;
import net.nice19.smboard.board.service.BoardService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/board")
public class BoardController {
	//DI
	private ApplicationContext context = new ClassPathXmlApplicationContext("/config/applicationContext.xml");
	private BoardService boardService = (BoardService) context.getBean("boardService");
	//
	
	// * User variable
	// article, page variables
	private int currentPage = 1;
	private int showArticleLimit = 10;	// 한페이지에 보여줄 게시물 크기.
	private int showPageLimit = 10;		// 페이지 블록 사이즈
	private int startArticleNum = 0;
	private int endArticleNum = 0;
	private int totalNum = 0;
	//
	
	// 파일 업로드 경로
	private String uploadPath = "C:\\springBoardUpload";
	//
	
	@RequestMapping("/list.do")
	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) {
		String type = null;
		String keyword = null;
		
		// request 파라미터에서 받아서 값 설정
		if(request.getParameter("page") == null || request.getParameter("page").trim().isEmpty() || request.getParameter("page").equals("0")) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		if(request.getParameter("type") != null) {
			type = request.getParameter("type").trim();
		}
		//
		
		// 게시물 변수 지정 
		startArticleNum  = (currentPage - 1) * showArticleLimit + 1;
		endArticleNum = startArticleNum + showArticleLimit - 1;
		//
		
		// boardList 얻어오고 page html 가져오기
		List<BoardModel> boardList;
		if(type != null && keyword != null) {
			boardList = boardService.searchArticle(type,  keyword,  startArticleNum,  endArticleNum);
			totalNum = boardService.getSearchTotalNum(type, keyword);
		}else {
			boardList = boardService.getBoardList(startArticleNum,  endArticleNum);
			totalNum = boardService.getTotalNum();
		}
		StringBuffer pageHtml = getPageHtml(currentPage, totalNum, showArticleLimit, showPageLimit, type, keyword);
		//
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardList", boardList);
		mav.addObject("pageHtml",pageHtml);
		mav.setViewName("/board/list");
		
		return mav;
	}
	
	// html 코드 생성 메소드
	private StringBuffer getPageHtml(int currentPate, int totalnum, int showArticleLimit, int showPageLimit, String type, String keyword) {
		StringBuffer pageHtml = new StringBuffer();
		int startPage = 0;
		int lastPage = 0;
		
		// 페이지 블록 설정
		startPage = ((currentPage - 1) / showPageLimit) * showPageLimit + 1;
		lastPage = startPage + showPageLimit - 1;
		
		if(lastPage > totalnum / showArticleLimit) {
			lastPage = (totalNum / showArticleLimit) + 1;
		}
		//
		
		// 페이징 html 코드 생성
		// if: 검색이 없을때
		if(type== null || keyword == null) {
			if(currentPage == 1) {
				pageHtml.append("<span>");
			}else {
				pageHtml.append("<span><a href=\"list.do?page=" + (currentPage-1) + "\"><이전></a>&nbsp&nbsp;");
			}
		}
	}

}
