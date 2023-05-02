package com.chanyongyang.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chanyongyang.jsp.domain.Member;
import com.chanyongyang.jsp.util.DBConn;

public class MemberDao {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	// stmt를 pstmt로 전부 변경해야함
	public int insert(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "insert into tbl_member(id, pw, name) values('"
				+ vo.getId() + "','" + vo.getPw() + "','" + vo.getName() + "')";
		try {
			// 문장 생성
			stmt = conn.createStatement();
			
			// 문장 처리
			result = stmt.executeUpdate(sql);
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return result;
	}
	public Member selectOne(String id) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Member vo = null;
		// 처리할 sql구문
		String sql = "select * from tbl_member where id = '" + id + "'";
		try {
			// 문장 생성 conn으로부터 문장생성 객체를 만듬
			stmt = conn.createStatement();
			
			// 결과집합 반환
			rs = stmt.executeQuery(sql);
			
			// 결과집합을 자바객체로 만듬
			if(rs.next()) {
				int idx = 1;
				// 객체 생성 후 값 바인딩
				vo = new Member(
						rs.getString(idx++),
						rs.getString(idx++),
						rs.getString(idx++),
						rs.getDate(idx++)
				);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return vo;
	}
	
	// 자원반환
	public void close() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {}
		}
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {}
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {}
		}
	}
	
	
	public static void main(String[] args) {
		MemberDao dao = new MemberDao();
//		dao.insert(new MemberVo("id1", "1234", "홍길동", null));
		
		System.out.println(dao.selectOne("id2"));
	}
}
