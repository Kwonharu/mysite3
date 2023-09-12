package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	
	public BoardDao() {
		//super();
	}
	
	
	private void getConnect() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	
	//게시글 목록 불러오기
	public List<BoardVo> boardListSelect() {

		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select b.no, ";
			query += " 		  b.title, ";
			query += " 		  u.name, ";
			query += " 		  b.hit, ";
			query += " 		  b.reg_date ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";

			//바인딩
			pstmt = conn.prepareStatement(query);
			
			//실행
			rs = pstmt.executeQuery();

			//결과처리
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				
				//vo로 묶고 list add
				BoardVo boardVo = new BoardVo();
				boardVo.setNo(no);
				boardVo.setTitle(title);
				boardVo.setName(name);
				boardVo.setHit(hit);
				boardVo.setRegDate(regDate);
				
				boardList.add(boardVo);
			
			}
			
			System.out.println(boardList);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		
		return boardList;
	}
	
	//게시글 불러오기
	public BoardVo boardSelect(int no) {

		BoardVo boardVo = null;
		
		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select u.name, ";
			query += " 		  b.hit, ";
			query += " 		  b.reg_date, ";
			query += " 		  b.title, ";
			query += " 		  b.content, ";
			query += " 		  b.no ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.no = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			//실행
			rs = pstmt.executeQuery();

			//결과처리
			rs.next();
			String name = rs.getString(1);
			int hit = rs.getInt(2);
			String regDate = rs.getString(3);
			String title = rs.getString(4);
			String content = rs.getString(5);
			int boardNo = rs.getInt(6);
			
			//vo로 묶기
			boardVo = new BoardVo(name, hit, regDate, title, content, boardNo);
			boardVo.setContent(boardVo.getContent().replace("\r\n", "<br>"));
			
			System.out.println("boardVo: "+boardVo);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		
		return boardVo;
	}
	
	
	//게시글 클릭 시 조회수 증가
	public int boardHitUpdate(int no) {
		int count = -1;
		
		this.getConnect();
		
		try {
			String query = "";
			query += " update board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query); 
			pstmt.setInt(1, no);
			
			//실행
			count = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}
	
	
	//게시글 작성
	public int boardInsert(BoardVo boardVo) { //Vo로 받았음
		int count = -1;

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, 0, SYSDATE, ?) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			
			//실행
			count = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}
	
	//게시글 삭제
	public int boardDelete(int no) {
		int count = -1;
		
		this.getConnect();
		
		try {
			String query = "";
			query += " delete board ";
			query += " where no = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query); 
			pstmt.setInt(1, no);
			
			//실행
			count = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}
	
	//게시글 수정
	public int boardUpdate(BoardVo boardVo) {
		int count = -1;
		
		this.getConnect();
		
		try {
			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += " 	   content = ? ";
			query += " where no = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			
			//실행
			count = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}
	
}






