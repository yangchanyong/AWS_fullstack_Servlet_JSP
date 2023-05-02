package com.chanyongyang.jsp.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chanyongyang.jsp.service.MemberService;
import com.chanyongyang.jsp.service.MemberServiceImpl;


@WebServlet("/member/logout")
public class Logout extends HttpServlet {
	private MemberService memberService = new MemberServiceImpl();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/");
	}


}
