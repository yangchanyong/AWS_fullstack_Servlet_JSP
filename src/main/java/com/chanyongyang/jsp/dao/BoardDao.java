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

import com.chanyongyang.jsp.domain.Board;
import com.chanyongyang.jsp.domain.Criteria;
import com.chanyongyang.jsp.domain.Member;
import com.chanyongyang.jsp.util.DBConn;

public class BoardDao {

		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;
		
		public Long insert(Board board) {
			conn = DBConn.getConnection();
			long result = 0;
			// 처리할 sql구문, 지정타입에 맞춰서 ? 로 바꿀수 있음 
			String sql = "insert into tbl_board (title, content, writer) values (?, ?, ?)";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setString(3, board.getWriter());
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
				// 쿼리 재실행
				// 작성한 게시글의 글번호 조회 , insert가 끝난시점에서 함
				sql = "select max(bno) from tbl_board";
				conn = DBConn.getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rs.next(); // 커서 이동(행간이동) / 조건식을 넣지 않은이유는 insert후에 실행되서 작성된글이 있다는걸 알기 때문 
				result = rs.getLong(1); // 1번컬럼을 long타입으로 반환
				close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		public Board selectOne(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			Board board = null;
			// 처리할 sql구문
			String sql = "select tb.*, (select count(*) from tbl_reply tr where tr.bno = tb.bno) replyCnt\r\n"
					+ "from tbl_board tb where bno = ?";
			try {
				// 문장 생성 conn으로부터 문장생성 객체를 만듬
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, bno);
				// 결과집합 반환
				rs = pstmt.executeQuery();
				
				// 결과집합을 자바객체로 만듬
				if(rs.next()) {
					int idx = 1;
					// 객체 생성 후 값 바인딩
					
					board = new Board(
							rs.getLong(idx++),
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getDate(idx++),
							rs.getString(idx++),
							rs.getInt(idx++), rs.getInt(idx++), null, rs.getInt(idx++));
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 결과 반환
			return board;
		}
		
		
		public List<Board> selectList(Criteria cri) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			List<Board> boards = new ArrayList<Board>();
			// 처리할 sql구문
			String sql =""; 
			sql += "select tb.*, (select count(*) from tbl_reply tr where tr.bno = tb.bno) replyCnt\r\n"
					+ "from tbl_board tb where category = ?";
			// 검색
			sql += getSearchSqlBy(cri);
			sql += " order by bno desc limit ? offset ?";
//			System.out.println(sql);
			try {
				// 문장 생성 conn으로부터 문장생성 객체를 만듬
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				pstmt.setInt(idx++, cri.getCategory());
//				if(cri.getType() != null && cri.getKeyword() != null) {
//					pstmt.setString(idx++, cri.getKeyword());
//				}
				pstmt.setInt(idx++, cri.getAmount());
				pstmt.setInt(idx++, (cri.getPageNum() - 1) * cri.getAmount());
				// 결과집합 반환
				rs = pstmt.executeQuery();
				
				// 결과집합을 자바객체로 만듬
				while(rs.next()) {
					idx = 1;
					// 객체 생성 후 값 바인딩
					
					Board board = new Board(
							rs.getLong(idx++),
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getDate(idx++),
							rs.getString(idx++),
							rs.getInt(idx++), rs.getInt(idx++), null, rs.getInt(idx++));
					boards.add(board);
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 결과 반환
			return boards;
		}
		public int selectListCount(Criteria cri) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			int count = 0;
			// 처리할 sql구문
			String sql = "select count(*) from tbl_board where category = ?";
			sql += getSearchSqlBy(cri);
			
			try {
				// 문장 생성 conn으로부터 문장생성 객체를 만듬
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, cri.getCategory());
				// 결과집합 반환
				rs = pstmt.executeQuery();
				
				// 결과집합을 자바객체로 만듬
				while(rs.next()) {
					int idx = 1;
					// 객체 생성 후 값 바인딩
					count = rs.getInt(1);
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 결과 반환
			return count;
		}
		
		public void update(Board board) {
			conn = DBConn.getConnection();
			// 처리할 sql구문
			String sql = "update tbl_board set\r\n"
					+ "	title = ?,\r\n"
					+ "	content = ?,\r\n"
					+ "	updatedate = now()\r\n"
					+ "where bno = ?";
//			System.out.println(sql);
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setLong(3, board.getBno());
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 조회수
		public void increaseHitCount(Long bno) {
			conn = DBConn.getConnection();
			// 처리할 sql구문
			String sql = "update tbl_board set\r\n"
					+ "	hitcount = hitcount+1\r\n"
					+ "where bno = ?";
//			System.out.println(sql);
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
		
		public void delete(Long bno) {
			conn = DBConn.getConnection();
			// 처리할 sql구문
			String sql = "delete from tbl_board where bno = ?";
//			System.out.println(sql);
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
		private String getSearchSqlBy(Criteria cri) {
			String sql ="";
			
//			if(cri.getType() != null && cri.getKeyword() != null) {
			if(cri.getType() != null && cri.getKeyword() != null && String.join("", cri.getType()).length() > 0) {
				sql += " and ( ";
							
				List<String> list = Arrays.asList(cri.getType());
				// T C W
				List<String> typeList = list.stream().map(s->" " + s + " like '%" + cri.getKeyword() + "%' ").collect(Collectors.toList());
				sql += String.join(" or ", typeList);
							
				sql += ")";
			}
			
			return sql;
		}
		
		public static void main(String[] args) {
//			new BoardDao().selectList(1).forEach(System.out::println);
//			BoardDao dao = new BoardDao();
//			Board board = dao.selectOne(5L);
//			System.out.println(dao.selectListCount(1));
//			board.setTitle("java에서 수정한 내용");
//			board.setContent("java에서 수정한 내용");
			
//			dao.update(board);
//			dao.delete(5L);
//			System.out.println(dao.selectOne(5L));
			
			String str = "12345";
			String[] result = str.split("");
			System.out.println(result.length);
			String result2 = String.join(" or ", result);
			System.out.println(result2);
		}

			

}
