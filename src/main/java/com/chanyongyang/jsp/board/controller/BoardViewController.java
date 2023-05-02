package com.chanyongyang.jsp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chanyongyang.jsp.domain.Criteria;
import com.chanyongyang.jsp.service.BoardService;
import com.chanyongyang.jsp.service.BoardServiceImpl;
import com.chanyongyang.jsp.util.ParamSolver;

@WebServlet("/board/view")
public class BoardViewController extends HttpServlet {
	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Criteria cri = ParamSolver.getParams(req, Criteria.class);
//		view.jsp에서도 cri로 호출 가능
		req.setAttribute("cri", cri);
		req.setAttribute("board", boardService.get(Long.valueOf(req.getParameter("bno"))));
		req.getRequestDispatcher("/WEB-INF/jsp/board/view.jsp").forward(req, resp);
	}
	
}
