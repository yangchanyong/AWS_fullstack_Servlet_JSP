package com.chanyongyang.jsp.service;

import java.util.List;

import com.chanyongyang.jsp.domain.Board;
import com.chanyongyang.jsp.domain.Criteria;
import com.chanyongyang.jsp.domain.Reply;

public interface ReplyService {
	
	// CRUD
	Long register(Reply reply);
	
	Reply get(Long rno);
	
	List<Reply> list(Long bno); // 파라미터 여하에 따라 추가처리

	int remove(Long rno);
}
