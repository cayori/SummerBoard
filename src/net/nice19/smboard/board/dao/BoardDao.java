package net.nice19.smboard.board.dao;

import java.util.List;

import net.nice19.smboard.board.model.BoardCommentModel;
import net.nice19.smboard.board.model.BoardModel;

public interface BoardDao {
	// jmboard 테이블 다 가져오기
	List<BoardModel> getBoardList(int startArticleNum, int showArticleLimit);
	
	// 선택항목 상세정보 가져오기
	BoardModel getOneArticle(int idx);
	
	// 검색결과 List 가져오기
	List<BoardModel> searchArticle(String type, String keyword, int startArticleNum, int endArticleNum);
	
	// 모든 댓글 가져오기
	List<BoardCommentModel> getCommentList(int idx);
	
	// 댓글 하나 가져오기
	BoardCommentModel getOneComment(int idx);
	
	// 게시물 수정하기
	boolean modifyArticle(BoardModel board);
	
	// 게시물 인서트
	boolean writeArticle(BoardModel board);
	
	// 댓글 인서트
	boolean writeComment(BoardCommentModel comment);
	
	// 조회수 업데이트
	void updateHitcount(int hitcount, int idx);
	
	// 추천 수 업데이트
	void updateRecommendCount(int recommendcount, int idx);
	
	// 게시물 숫자
	int getTotalNum();
	
	// 검색결과 숫자
	int getSearchTotalNum(String type, String keyword);
	
	// 댓글 삭제
	void deleteComment(int idx);
	
	// 게시물 삭제
	void deleteArticle(int idx);
}
