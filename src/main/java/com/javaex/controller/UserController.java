package com.javaex.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//업무구분용 파라미터 체크
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) {
			
			//회원가입 폼(action=joinForm)
			System.out.println("action=joinForm");
			
			//foward
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinForm.jsp");
			//rd.forward(request, response);
			
			//forward를 util 사용
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");


		}else if("join".equals(action)) {
			
			//회원가입(action=join)	
			System.out.println("action=join");
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			String name = request.getParameter("name");
			
			//1개로 묶기
			UserVo userVo = new UserVo(id, name, password, gender);
			System.out.println(userVo);
			
			//dao로 db 작업
			UserDao userDao = new UserDao();
			int count = userDao.userInsert(userVo);
			System.out.println(count);
			
			response.sendRedirect("user?action=joinOk");
			
		}else if("joinOk".equals(action)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(action)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
