package com.chanyongyang.jsp.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiChannel;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/file")
public class FileController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("file.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String id = req.getParameter("id");
//		String file = req.getParameter("file");
		
		// req, 
		// 파일의 위치(권한이 가능한곳(내가 만든 폴더) 권한이 없을시 거부당할수있음, 
		// 수치값(아래는 2mb), 
		// 파일명에 대한 처리
		// 파일명 중복처리
		MultipartRequest multipartRequest = new MultipartRequest(req, "C:\\upload", 2 * 1024 * 1024, "utf-8",
				new DefaultFileRenamePolicy());
		String id = multipartRequest.getParameter("id");
		//String file = multipartRequest.getParameter("file");
//		File f = multipartRequest.getFile("file");
		System.out.println(id);
		//System.out.println(file);
		
		// 파일명
		String origin = multipartRequest.getOriginalFileName("file");
		// 저장되는 파일명(중복되는 파일이름 다른것으로 변경)
		String realName = multipartRequest.getFilesystemName("file");
//		multipartRequest.getFile("file").
		System.out.println(origin);
		System.out.println(realName);

		
		File file = new File("c:\\upload\\");
	}

	
}
