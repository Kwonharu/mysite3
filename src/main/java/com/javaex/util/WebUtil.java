package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
	//필드 X
	
	//생성자 -> default
	
	//메소드gs 필드 읎서요
	
	//메소드일반
	public static void forward(HttpServletRequest request,
			   				   HttpServletResponse response,
							   String path) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}
	
	public static void redirect(HttpServletRequest request,
						 		HttpServletResponse response,
						 		String url) throws IOException {
		
		response.sendRedirect(url);
	}
	
}








