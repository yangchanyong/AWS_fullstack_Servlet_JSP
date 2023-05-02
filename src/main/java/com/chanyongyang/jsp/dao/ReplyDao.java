package com.chanyongyang.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chanyongyang.jsp.domain.Board;
import com.chanyongyang.jsp.domain.Reply;
import com.chanyongyang.jsp.util.DBConn;

public class ReplyDao {

		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;
		
		public int insert(Reply reply) {
			conn = DBConn.getConnection();
			int result = 0;
			// 처리할 sql구문, 지정타입에 맞춰서 ? 로 바꿀수 있음 
			String sql = "insert into tbl_reply (content, writer, bno) values (?, ?, ?)";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, reply.getContent());
				pstmt.setString(2, reply.getWriter());
				pstmt.setLong(3, reply.getBno());
				
				// 문장 처리
				result = pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

		
		public List<Reply> selectList(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			List<Reply> replys = new ArrayList<Reply>();
			// 처리할 sql구문
			String sql =""; 
			sql += "select * from tbl_reply where bno = ?";
//			System.out.println(sql);
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
					
					Reply reply = new Reply(
							rs.getLong(idx++),
							rs.getString(idx++),
							rs.getDate(idx++),
							rs.getString(idx++),
							rs.getLong(idx++));
					replys.add(reply);
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 결과 반환
			return replys;
		}
		public int delete(Long rno) {
			int ret = 0;
			conn = DBConn.getConnection();
			// 처리할 sql구문
			String sql = "delete from tbl_reply where rno = ?";
//			System.out.println(sql);
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, rno);
				
				// 문장 처리
				ret = pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
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
		
		public Reply selectOne(Long rno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			Reply reply = null;
			String sql = "";
			// 처리할 sql구문
			sql = "select * from tbl_reply where rno = ?";
			try {
				// 문장 생성 conn으로부터 문장생성 객체를 만듬
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				pstmt.setLong(idx++, rno);
				// 결과집합 반환
				rs = pstmt.executeQuery();
				
				// 결과집합을 자바객체로 만듬
				while(rs.next()) {
					// 객체 생성 후 값 바인딩
					idx = 1;
					reply = new Reply(
							rs.getLong(idx++),
							rs.getString(idx++),
							rs.getTimestamp(idx++),
							rs.getString(idx++),
							rs.getLong(idx++));
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 결과 반환
			return reply;
		}
		public static void main(String[] args) {
//			new ReplyDao().selectList(8).forEach(System.out::println);
			ReplyDao dao = new ReplyDao();
//			System.out.println(dao.selectList(515L));
//			System.out.println(dao.selectListCount(1));
//			reply.setTitle("java에서 수정한 내용");
//			reply.setContent("java에서 수정한 내용");
			
//			dao.update(reply);
			dao.delete(8L);
//			System.out.println(dao.selectOne(5L));
			
//			String str = "12345";
//			String[] result = str.split("");
//			System.out.println(result.length);
//			String result2 = String.join(" or ", result);
//			System.out.println(result2);
		}

}
