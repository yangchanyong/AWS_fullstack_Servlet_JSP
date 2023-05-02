package com.chanyongyang.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;import com.chanyongyang.jsp.domain.Member;
import com.chanyongyang.jsp.domain.Reply;
import com.chanyongyang.jsp.service.ReplyService;
import com.chanyongyang.jsp.service.ReplyServiceImpl;
import com.chanyongyang.jsp.util.ParamSolver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/reply")
public class ReplyController extends HttpServlet {
	// Http Method
	// GET, POST, PUT(PATCH), DELETE
	private ReplyService service = new ReplyServiceImpl();
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { // 조회
		// 목록을 가져와야함 (bno값 필요)
		Long bno = Long.valueOf(req.getParameter("bno"));
		List<Reply> replies = service.list(bno);
//		System.out.println(replies);
		Gson gson = new Gson();
		// 날짜, 시간 출력방식 변경
//		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gson.toJson(replies); // String type으로 반환
//		System.out.println(json);
		resp.setContentType("application/json; charset=utf8");
		resp.getWriter().print(json);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { // 삭제
		// 로그인 여부
		
		// 본인 댓글인지 확인
		Long rno = Long.valueOf(req.getParameter("rno"));
		PrintWriter out = resp.getWriter();
		Reply reply = service.get(rno);
		if(reply != null && ParamSolver.isMine(req, service.get(rno).getWriter())) {
			out.print(service.remove(rno));
		}
		else {
			out.print(0);
		}
//		System.out.println(req.getParameter("rno"));
//		System.out.println("doDelete()");
//		service.remove(Long.valueOf(req.getParameter("rno")));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reply reply = ParamSolver.getParams(req, Reply.class);
//		System.out.println(reply);
//		System.out.println("doPost()");
		service.register(reply);

	}
	
	
}
