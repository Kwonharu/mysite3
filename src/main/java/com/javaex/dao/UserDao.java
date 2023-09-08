package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	//필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//생성자
	public UserDao() {
		//super();
	}
	
	//메소드gs
		
	//메소드 일반
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

	//회원가입
	public int userInsert(UserVo userVo) { //Vo로 받았음
		int count = -1;

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
			//실행
			count = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}
	
	//로그인
	public UserVo userSelect(UserVo userVo) {

		UserVo authUser = null;
		
		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select no, ";
			query += " 		  name ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			
			//실행
			rs = pstmt.executeQuery();

			//결과처리
			rs.next();
			int no = rs.getInt(1);
			String name = rs.getString(2);
			
			//vo로 묶기
			authUser = new UserVo();
			authUser.setNo(no);
			authUser.setName(name);
			
			System.out.println(authUser);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		
		return authUser;
	}
	
	
	public int userUpdate(UserVo userVo) { //Vo로 받았음
		int count = -1;

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " update users ";
			query += " set  password = ?, ";
			query += " 		name = ?, ";
			query += " 		gender = ? ";
			query += " where no = ? ";
			     			
			//바인딩
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, userVo.getPassword());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			//실행
			count = pstmt.executeUpdate();
			
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}
	
	//업데이트를 위해 다 가져오기
	public UserVo userSelectAll(int num) {

		UserVo userVoAll = null;
		
		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select no, ";
			query += " 		  name, ";
			query += " 		  id, ";
			query += " 		  password, ";
			query += " 		  gender ";
			query += " from users ";
			query += " where no = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);
			
			//실행
			rs = pstmt.executeQuery();

			//결과처리
			rs.next();
			int no = rs.getInt(1);
			String name = rs.getString(2);
			String id = rs.getString(3);
			String pw = rs.getString(4);
			String gender = rs.getString(5);
			
			//vo로 묶기
			userVoAll = new UserVo(no, id, name, pw, gender);
			
			System.out.println("userVoAll(userSelectAll) "+userVoAll);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		
		return userVoAll;
	}
	
}







