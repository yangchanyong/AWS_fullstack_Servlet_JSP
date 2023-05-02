package com.chanyongyang.jsp.member.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chanyongyang.jsp.service.MemberService;
import com.chanyongyang.jsp.service.MemberServiceImpl;


@WebServlet("/member/login")
public class Login extends HttpServlet {
	private MemberService memberService = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/member/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
//		int code = memberService.login(id, pw);	//
		
		String msg = "";
		String redirectStr = req.getContextPath() + "/";
		String href = req.getParameter("href");
		switch(memberService.login(id, pw)) {
		case 1:
			req.getSession().setAttribute("member", memberService.get(id));
			if(href != null) {
				redirectStr = href;
			}
//			resp.sendRedirect(req.getContextPath()); //
			break;
		case 2: 
			msg = "아이디가 없습니다";
			msg = URLEncoder.encode(msg, "utf-8");
//			resp.sendRedirect(req.getContextPath() + "/member/login?msg="+msg); //
			redirectStr += "member/login?msg="+msg;
			if(href != null) {
				redirectStr += "&href=" + URLEncoder.encode(href, "utf-8");
			}
			break;
		case 3: 
			msg = "비밀번호가 일치 하지 않습니다";
			msg = URLEncoder.encode(msg, "utf-8"); // 맨 아래로
//			resp.sendRedirect(req.getContextPath() + "/member/login?msg="+msg);
			redirectStr += "member/login?msg="+msg;
			if(href != null) {
				redirectStr += "&href=" + URLEncoder.encode(href, "utf-8");
			}
		}
		resp.sendRedirect(redirectStr);
	}
	

}


//		Member vo = dao.selectOne(id);
//		if(vo == null) {
//			// 아이디 없음
//			System.out.println("아이디 없음");
//		}
//		else if(!vo.getPw().equals(pw)) {
//			// 비밀번호 틀림
//			System.out.println("비밀번호 틀림");
//		}
//		else {
//			// 로그인 성공
//			HttpSession session = req.getSession();
//			session.setAttribute("member", vo);
//		}