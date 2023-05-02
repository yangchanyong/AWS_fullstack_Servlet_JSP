package com.chanyongyang.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.chanyongyang.jsp.domain.Attach;
import com.chanyongyang.jsp.domain.Criteria;
import com.chanyongyang.jsp.domain.Member;
import com.chanyongyang.jsp.util.DBConn;

public class AttachDao {

		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;
		
		public void insert(Attach attach) {
			conn = DBConn.getConnection();
			// 처리할 sql구문, 지정타입에 맞춰서 ? 로 바꿀수 있음 
			String sql = "insert into tbl_attach values (?, ?, ?, ?, ?)";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				pstmt.setString(idx++, attach.getUuid());
				pstmt.setString(idx++, attach.getOrigin());
				pstmt.setBoolean(idx++, attach.isImage());
				pstmt.setString(idx++, attach.getPath());
				pstmt.setLong(idx++, attach.getBno());
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public Attach selectOne(String uuid) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			Attach attach = null;
			// 처리할 sql구문
			String sql = "select * from tbl_attach where uuid = ?";
			try {
				// 문장 생성 conn으로부터 문장생성 객체를 만듬
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, uuid);
				// 결과집합 반환
				rs = pstmt.executeQuery();
				
				// 결과집합을 자바객체로 만듬
				if(rs.next()) {
					int idx = 1;
					// 객체 생성 후 값 바인딩
					
					attach = new Attach(
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getBoolean(idx++),
							rs.getString(idx++),
							rs.getLong(idx++));
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 결과 반환
			return attach;
		}
		
		
		public List<Attach> selectList(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			List<Attach> attachs = new ArrayList<Attach>();
			// 처리할 sql구문
			String sql ="select * from tbl_attach where bno = ?"; 
			// 검색
			try {
				// 문장 생성 conn으로부터 문장생성 객체를 만듬
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				pstmt.setLong(idx++, bno);
				// 결과집합 반환
				rs = pstmt.executeQuery();
				
				// 결과집합을 자바객체로 만듬
				while(rs.next()) {
					idx = 1;
					// 객체 생성 후 값 바인딩
					
					Attach	attach = new Attach(
								rs.getString(idx++),
								rs.getString(idx++),
								rs.getBoolean(idx++),
								rs.getString(idx++),
								rs.getLong(idx++));
					attachs.add(attach);
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 결과 반환
			return attachs;
		}
		
		public void delete(Long bno) {
			conn = DBConn.getConnection();
			// 처리할 sql구문
			String sql = "delete from tbl_attach where bno = ?";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, bno);
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 자원반환
		public void close() {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
		}

		public static void main(String[] args) {
//			new AttachDao().selectList(1).forEach(System.out::println);
//			AttachDao dao = new AttachDao();
//			Attach attach = dao.selectOne(5L);
//			System.out.println(dao.selectListCount(1));
//			attach.setTitle("java에서 수정한 내용");
//			attach.setContent("java에서 수정한 내용");
			
//			dao.update(attach);
//			dao.delete(5L);
//			System.out.println(dao.selectOne(5L));
			
			String str = "12345";
			String[] result = str.split("");
			System.out.println(result.length);
			String result2 = String.join(" or ", result);
			System.out.println(result2);
		}

			

}
