package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {

	// 필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자
	public GuestDao() {
	}

	// 메소드 gs

	// 메소드 일반
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

	// 사람등록
	public int guestInsert(GuestVo guestBookVo) { //Vo로 받았음
		int count = -1;

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_id.nextval, ?, ?, ?, SYSDATE) ";

			pstmt = conn.prepareStatement(query);

			// 바인딩 vo에서 값을 setter로 꺼낸다
			pstmt.setString(1, guestBookVo.getName());
			pstmt.setString(2, guestBookVo.getPassword());
			pstmt.setString(3, guestBookVo.getContent());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			// System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	// 사람삭제
	public int guestDelete(String guestbookPW) {

		int count = -1;

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " delete from guestbook ";
			query += " where password = ? ";

			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, guestbookPW);


			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			// System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;

	}


		
	//수정
	public int guestUpdate(GuestVo guestBookVo) {

		int count = -1;

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " update guestbook ";
			query += " set name = ?, ";
			query += " 	   content = ?, ";
			query += "     reg_date = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, guestBookVo.getName());
			pstmt.setString(2, guestBookVo.getContent());
			pstmt.setString(3, guestBookVo.getReg_date());
			pstmt.setInt(4, guestBookVo.getNo());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			// System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;

	}

	
	// 검색
	public List<GuestVo> guestSelect(String keyword) {

		List<GuestVo> guestBookList = new ArrayList<GuestVo>();

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         content, ";
			query += "         reg_date ";
			query += " from guestbook ";
			/**********************************************************************/
			if(!keyword.equals("")) { //keyword가 ""가 아니면 ==> keyword가 있으면 검색
				query += " where name like ? ";
			}
			query += " order by no ";
			
			pstmt = conn.prepareStatement(query);

			// 바인딩**********************************************************************
			if(!keyword.equals("")) { //keyword가 ""가 아니면 ==> keyword가 있으면 검색
				pstmt.setString(1, "%"+keyword+"%");
			}

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {

				int no = rs.getInt(1);
				String name = rs.getString(2);
				String content = rs.getString(3);
				String reg_date = rs.getString(4);

				GuestVo guestBookVo = new GuestVo(no, name, content, reg_date);

				guestBookList.add(guestBookVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return guestBookList;

	}
	
	public GuestVo getGuest(int id) {

		GuestVo guestBookVo = null;

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         content, ";
			query += "         reg_date ";
			query += " from guestbook ";
			query += " where person_id = ? ";
			
			pstmt = conn.prepareStatement(query);

			//바인딩
			pstmt.setInt(1, id);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {

				int no = rs.getInt(1);
				String name = rs.getString(2);
				String content = rs.getString(3);
				String reg_date = rs.getString(4);

				guestBookVo = new GuestVo(no, name, content, reg_date);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return guestBookVo;

	}
}



