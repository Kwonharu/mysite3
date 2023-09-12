package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;


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
			
			
		}else if("modifyForm".equals(action)) {
			//게시글 수정
			System.out.println("action: modifyForm");
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
			
		}else {
			System.out.println("읎서요");
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
