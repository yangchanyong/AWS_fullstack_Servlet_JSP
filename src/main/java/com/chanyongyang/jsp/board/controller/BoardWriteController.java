package com.chanyongyang.jsp.board.controller;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chanyongyang.jsp.domain.Board;
import com.chanyongyang.jsp.service.BoardService;
import com.chanyongyang.jsp.service.BoardServiceImpl;
import com.chanyongyang.jsp.util.ParamSolver;
import static com.chanyongyang.jsp.util.ParamSolver.*;
@MultipartConfig(location = ParamSolver.UPLOAD_PATH, fileSizeThreshold = 1024)
@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet {
	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getRequestURI());
//		System.out.println(req.getContentType());
//		if(req.getSession().getAttribute("member") == null) {
		if(!isLogin(req)) {
			resp.sendRedirect(req.getContextPath() + "/member/login?href=" + URLEncoder.encode(req.getRequestURI(), "utf-8"));
			return;
		}
		req.getRequestDispatcher("/WEB-INF/jsp/board/write.jsp").forward(req, resp);
	}

	@Override //글작성 주로 나오는 오류는 네임값오류이다. 이름이 맞는지 getParameter에 네임값이 맞는지 꼭 확인하자!
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!isLogin(req)) {
			resp.sendRedirect(req.getContextPath() + "/member/login?href=" + URLEncoder.encode(req.getRequestURI(), "utf-8"));
			return;
		}
		
//		Board board = new Board(req.getParameter("title"), req.getParameter("content"), req.getParameter("writer"));
//		ParamSolver를 만든이유는 위 코드가 아래 코드만큼 짧아진다.
		Board board = ParamSolver.getParams(req, Board.class);
		System.out.println(board);
		boardService.register(board);
		resp.sendRedirect("list");
	}
	
	
	
}
