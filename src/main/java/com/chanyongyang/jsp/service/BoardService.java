package com.chanyongyang.jsp.service;

import java.util.List;

import com.chanyongyang.jsp.domain.Board;
import com.chanyongyang.jsp.domain.Criteria;

public interface BoardService {
	
	// CRUD
	Long register(Board board);
	
	Board get(Long bno);
	
	List<Board> list(Criteria cri); // 파라미터 여하에 따라 추가처리

	int listCount(Criteria cri);
	
	void modify(Board board);
	
	void remove(Long bno);
}
