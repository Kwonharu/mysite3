package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			
			//회원가입 성공 시
			if(count != -1){
				
				WebUtil.redirect(request, response, "user?action=joinOk");
				//response.sendRedirect("user?action=joinOk");
				
			//회원가입 실패 시
			}else {
				WebUtil.redirect(request, response, "/mysite3/user?action=joinForm&result=fail");
			}

			
		}else if("joinOk".equals(action)) {
			//가입 성공
			System.out.println("action=joinOk");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(action)) {
			//로그인폼
			System.out.println("action=loginForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}else if("login".equals(action)) {
			//로그인
			System.out.println("action=login");
			
			//파라미터 꺼내오기
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			//vo로 묶기
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(pw);
			
			//dao를 통해 로그인한 사용자가 있는지 확인
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.userSelect(userVo);
			
			/*
			System.out.println(authUser);
			authUser != null : 로그인 성공
			authUser == null : 로그인 실패
			*/
			
			if(authUser != null){
				//세션에 값 넣기 (로그인)
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);

				//redirect to main
				WebUtil.redirect(request, response, "/mysite3/main");
				
			}else {
				//컷!
				WebUtil.redirect(request, response, "/mysite3/user?action=loginForm&result=fail");
			}
			
		}else if("logout".equals(action)) {
			//로그인폼
			System.out.println("action=logout");
			
			HttpSession session = request.getSession();
			//세션의 모든 값을 지움.
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite3/main");
			
		}else if("modifyForm".equals(action)) {
			//수정폼
			System.out.println("action=modifyForm");
			
			HttpSession session = request.getSession();
			
			//로그인을 했으면
			if(session.getAttribute("authUser") != null) {
			    //db에서 회원정보가져오기 no-->  vo<---
				UserDao userDao = new UserDao();
				
				UserVo userVo = (UserVo) session.getAttribute("authUser");
				int no = userVo.getNo();
				UserVo userVoAll = userDao.userSelectAll(no);
				
				request.setAttribute("userVoAll", userVoAll);
				
			    WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
			//로그인을 안했으면
			}else {
				System.out.println("세션에 값이 없음 == 로그인 안 함");
				
				//로그인폼
				WebUtil.redirect(request, response, "/mysite3/user?action=loginForm");
			}
			
		}else if("modify".equals(action)) {
			//수정
			System.out.println("action=modify");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo(no, id, name, pw, gender);
			UserDao userDao = new UserDao();
			userDao.userUpdate(userVo);
			
			//세션 업데이트
			UserVo authUser = userDao.userSelect(userVo);
			
			System.out.println("authUser(controller) : "+authUser); 
			
			if(authUser != null){
				//db 업데이트 후 세션 업데이트
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);

				//redirect to main
				WebUtil.redirect(request, response, "/mysite3/main");
				
			}else {
				//컷!
				WebUtil.redirect(request, response, "/mysite3/user?action=modifyForm&result=fail");
			}
			
		}else {
			System.out.println("따흐흑");
			
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
