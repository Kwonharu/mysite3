package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;


@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("addList".equals(action)) {
			//리스트 출력
			System.out.println("addList");
			
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestList = guestDao.guestSelect("");

			request.setAttribute("guestList", guestList);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if("insert".equals(action)) {
			//방명록 등록
			
			System.out.println("action=insert");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestVo guestVo = new GuestVo(name, password, content);
			
			GuestDao guestDao = new GuestDao();
			int count = guestDao.guestInsert(guestVo);
			System.out.println(count);
			
			response.sendRedirect("/mysite3/guest?action=addList");
			
		} else if("deleteForm".equals(action)) {
			//삭제폼
			
			System.out.println("deleteForm");
			
			int no = Integer.parseInt(request.getParameter("no"));
			/*
			 * request.setAttribute("personId", personId);
			 * 
			 * 이미 request Parameter에 id가 있기 때문에 할 필요 x
			 * -> 받은 Parameter 외에 새롭게 가공한 data가 있을 땐 Attribute에 넣음
			 */

		    WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		    
		} else if("delete".equals(action)) {
			//삭제
			
			System.out.println("action=delete");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			GuestDao guestDao = new GuestDao();
			GuestVo guestVo = guestDao.getGuest(no);
			
			//파라미터로 가져온 pw와 db에서 가져온 pw가 일치하는지 확인
			if(guestVo.getPassword().equals(password)) {

				int count = guestDao.guestDelete(no);
				
				System.out.println(count);

			} else {
				System.out.println("삭제 실패");
			}
			
			response.sendRedirect("/mysite3/guest?action=addList");
		}else {
			System.out.println("무요");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
