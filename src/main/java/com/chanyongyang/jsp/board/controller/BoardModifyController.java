package com.chanyongyang.jsp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chanyongyang.jsp.domain.Board;
import com.chanyongyang.jsp.domain.Criteria;
import com.chanyongyang.jsp.service.BoardService;
import com.chanyongyang.jsp.service.BoardServiceImpl;
import com.chanyongyang.jsp.util.ParamSolver;

@WebServlet("/board/modify")
public class BoardModifyController extends HttpServlet {
	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("member") == null) {
			resp.sendRedirect(req.getContextPath() + "/member/login");
			return;
		}
		req.setAttribute("cri", ParamSolver.getParams(req, Criteria.class));
		req.setAttribute("board", boardService.get(Long.valueOf(req.getParameter("bno"))));
		req.getRequestDispatcher("/WEB-INF/jsp/board/modify.jsp").forward(req, resp);
	}

	@Override //글작성 주로 나오는 오류는 네임값오류이다. 이름이 맞는지 getParameter에 네임값이 맞는지 꼭 확인하자!
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		Board board = new Board(req.getParameter("title"), req.getParameter("content"), req.getParameter("writer"));
//		board.setBno(Long.valueOf(req.getParameter("bno")));
		Criteria cri = ParamSolver.getParams(req, Criteria.class);
		Board board = ParamSolver.getParams(req, Board.class);
		boardService.modify(board);
		resp.sendRedirect("view?bno=" + board.getBno() + "&" + cri.getFullQueryString());
	}
	
	
	
}
