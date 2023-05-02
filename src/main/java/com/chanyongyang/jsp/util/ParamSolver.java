package com.chanyongyang.jsp.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.chanyongyang.jsp.domain.Attach;
import com.chanyongyang.jsp.domain.Board;
import com.chanyongyang.jsp.domain.Criteria;
import com.chanyongyang.jsp.domain.Member;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;

public class ParamSolver {
	public static final String UPLOAD_PATH = "c:/upload";
	
	// T는 클래스타입, clazz의 Class<T> 타입으로 만들어줌
	public static <T> T getParams(HttpServletRequest req, Class<T> clazz) {

		// 인스턴스 생성
		T t = null;
		try {
			t = clazz.getDeclaredConstructor().newInstance();
			
			// 선언 필드에 대한 타입 및 이름 체크
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields) {
				String fieldName = f.getName();
				String setterName = "set" + f.getName().substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method[] methods = clazz.getDeclaredMethods();
				for(Method m : methods) {
					if(setterName.equals(m.getName())) {
						if(req.getParameter(fieldName) == null) {
							continue;
						}
						if(f.getType() == Integer.class || f.getType() == int.class) {
							m.invoke(t, Integer.parseInt(req.getParameter(fieldName)));
						}
						if(f.getType() == String.class) {
							m.invoke(t, req.getParameter(fieldName));
						}
						if(f.getType() == String[].class) {
							m.invoke(t, (Object)req.getParameterValues(fieldName));
						}
						if(f.getType() == Long.class || f.getType() == long.class) {
							m.invoke(t, Long.valueOf(req.getParameter(fieldName)));
						}
					}
				}
			}
			if(req.getContentType() == null || !req.getContentType().startsWith("multipart")) {
				return t;
			}
			
			Collection<Part> parts = req.getParts();
			
			List<Attach> attachs = new ArrayList<Attach>();
			for(Part p : parts) {
				if(p.getContentType() == null) {
					continue;
				}
				// 파일의 원본이름
				String origin = p.getSubmittedFileName();

				// 파일명 중 마지막 .의 위치
				int dotIdx = origin.lastIndexOf(".");

				// 확장자를 담을 변수
				String ext = "";

				// 확장자 구하기
				if (dotIdx > -1) {
					ext = origin.substring(dotIdx); // 확장자명이 없는경우 처리
				}

				// UUID 문자열 생성
				String uuid = UUID.randomUUID().toString();

				// thumbnail
				// 경로 문자열 반환
				String path = getTodayStr();

				// 경로 문자열에 대한 폴더 생성
				File targetPath = new File(ParamSolver.UPLOAD_PATH, path);
				if (!targetPath.exists()) {
					targetPath.mkdirs();
				}

				// 원본에 대한 저장
				File fs = new File(targetPath, uuid + ext);
				p.write(fs.getPath());

				// 이미지 여부 확인
				// image/x-icon, image/webp
//				System.out.println(p.getContentType());
//					List<String> exceptImgMimes = Arrays.asList("image/x-icon", "image/webp");
				boolean image = p.getContentType().startsWith("image"); // &&
																		// !exceptImgMimes.contains(p.getContentType());

				if (image) {
					// 썸네일 생성
					try {
						File out = new File(targetPath, uuid + "_t" + ext);
						Thumbnailator.createThumbnail(fs, out, 200, 200);
					} 
					catch (UnsupportedFormatException ignore) {image = false;}
				}
				attachs.add(new Attach(uuid, origin, image, path));
				// uuid, origin, image, path
//				attachs.forEach(System.out::println);
			}
			if(clazz == Board.class) {
				((Board)t).setAttachs(attachs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	// 로그인 여부 확인
	public static boolean isLogin(HttpServletRequest req) {
		return req.getSession().getAttribute("member") != null;
	}
	
	// 본인인지 확인
	public static boolean isMine(HttpServletRequest req, String writer) {
		return isLogin(req) && ((Member)req.getSession().getAttribute("member")).getId().equals(writer);
	}

	private static String getTodayStr() {
		return new SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis());
	}
	public static void main(String[] args) {
		getParams(null, Criteria.class);
	}
}
