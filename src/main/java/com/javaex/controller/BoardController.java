package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			//리스트 출력
			System.out.println("action: list");
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.boardListSelect();
			
			request.setAttribute("boardList", boardList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

			
		}else if("read".equals(action)) {
			//게시글 읽기
			System.out.println("action: read");
			
			//넘어온 파라미터
			int no = Integer.parseInt(request.getParameter("no"));
			
			System.out.println("no: "+ no);
			
			//파라미터로 bd에서 추출 -> vo로 저장 후 넘기기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.boardSelect(no);
			request.setAttribute("boardVo", boardVo);
			
			//조회수 증가
			int count = boardDao.boardHitUpdate(no);
			System.out.println("boardHitUpdate: "+count);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
			
		}else if("writeForm".equals(action)) {
			//게시글 작성 폼
			System.out.println("action: writeForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
			
		}else if("insert".equals(action)) {
			//게시글 작성
			System.out.println("action: insert");
			
			HttpSession session = request.getSession();
			
			if(session.getAttribute("authUser") != null) {
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				int userNo = Integer.parseInt(request.getParameter("userNo"));
				
				BoardVo boardVo = new BoardVo();
				boardVo.setTitle(title);
				boardVo.setContent(content);
				boardVo.setUserNo(userNo);
				
				BoardDao boardDao = new BoardDao();
				int count = boardDao.boardInsert(boardVo);
				System.out.println("insert count: "+ count);
				
				WebUtil.redirect(request, response, "/mysite3/board?action=list");
			}else {
				System.out.println("세션에 값이 없음 == 로그인 안 했구나 이녀석 어딜 글을 쓰려고 감히");
				
				WebUtil.redirect(request, response, "/mysite3/user?action=loginForm");
			}
			
		}else if("delete".equals(action)) {
			//게시글 삭제
			System.out.println("action: delete");
			
			//session에서 유저 이름 가져오기
			HttpSession session = request.getSession();
			UserVo userVo = (UserVo) session.getAttribute("authUser");
			String userName = userVo.getName();
			
			//게시글 작성자 가져오기
			BoardDao boardDao = new BoardDao();
			
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boradVo = boardDao.boardSelect(no);
			String boardName = boradVo.getName();
			
			//세션의 name == board의 name 일 경우
			if(userName.equals(boardName)) {
				int count = boardDao.boardDelete(no);
				System.out.println("delete count: "+ count);
				
				WebUtil.redirect(request, response, "/mysite3/board?action=list");
			}else {
				WebUtil.forward(request, response, "/WEB-INF/views/board/denied.jsp");
			}
			
		}else if("modifyForm".equals(action)) {
			//게시글 수정 폼
			System.out.println("action: modifyForm");
			
			//넘어온 파라미터
			int no = Integer.parseInt(request.getParameter("no"));
			
			System.out.println("no: "+ no);
			
			//파라미터로 bd에서 추출 -> vo로 저장 후 넘기기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.boardSelect(no);
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
			
		}else if("update".equals(action)) {
			//게시글 수정
			System.out.println("action: update");
			
			HttpSession session = request.getSession();
			
			if(session.getAttribute("authUser") != null) {
				
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				int no = Integer.parseInt(request.getParameter("no"));
				
				BoardVo boardVo = new BoardVo(title, content, no);
				
				BoardDao boardDao = new BoardDao();
				int count = boardDao.boardUpdate(boardVo);
				System.out.println("boardUpdate count: "+count);
				
				WebUtil.redirect(request, response, "/mysite3/board?action=list");
			
			}else{
				WebUtil.redirect(request, response, "/mysite3/user?action=loginForm");
			}
			
		}else {
			System.out.println("읎서요");
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
