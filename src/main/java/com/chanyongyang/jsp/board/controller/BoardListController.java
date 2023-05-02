package com.chanyongyang.jsp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chanyongyang.jsp.domain.Criteria;
import com.chanyongyang.jsp.domain.PageDto;
import com.chanyongyang.jsp.service.BoardService;
import com.chanyongyang.jsp.service.BoardServiceImpl;
import com.chanyongyang.jsp.util.ParamSolver;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {
	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 클래스 리터럴 필요
		Criteria criteria = ParamSolver.getParams(req, Criteria.class);
//		System.out.println(criteria);
		// 
//		try {
//			int pageNum = Integer.parseInt(req.getParameter("page"));
//			int amount = Integer.parseInt(req.getParameter("amount"));
//			criteria = new Criteria(pageNum, amount);
//		}
////		numberformatexception 발생 방지
//		catch (Exception e) {
//			criteria = new Criteria();
//		}
//		System.out.println(criteria);
		
//		System.out.println(criteria);
		
		req.setAttribute("boards", boardService.list(criteria));
		req.setAttribute("page", new PageDto(boardService.listCount(criteria), criteria));
		req.getRequestDispatcher("/WEB-INF/jsp/board/list.jsp").forward(req, resp);
	}
	
}
