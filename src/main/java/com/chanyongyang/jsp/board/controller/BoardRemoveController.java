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

@WebServlet("/board/remove")
public class BoardRemoveController extends HttpServlet {
	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Criteria cri = ParamSolver.getParams(req, Criteria.class);
		boardService.remove(Long.valueOf(req.getParameter("bno")));
		// 삭제시 쿼리스트링을 달고다님
		resp.sendRedirect("list" + "?" + cri.getFullQueryString());
	}
	
}
