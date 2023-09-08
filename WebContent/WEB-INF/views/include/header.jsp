<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.UserVo"%>

<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");
	/* System.out.println(authUser); */
	
	/*
	authUser == null : 로그인 X
	authUser != null : 로그인 성공
	*/
%>

<div id="header" class="clearfix">
	<h1>
		<a href="/mysite3/main">MySite</a>
	</h1>

	<%if(authUser != null){ %> <!-- 로그인 성공 -->
		<ul>
			<li><%=authUser.getName()%> 님 안녕하세요^^7</li>
			<li><a href="/mysite3/user?action=logout" class="btn_s">로그아웃</a></li>
			<li><a href="/mysite3/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
		</ul>
	<%}else{ %> <!-- 로그인 실패 / 안 함 -->
		<ul>
			<li><a href="/mysite3/user?action=loginForm" class="btn_s">로그인</a></li>
			<li><a href="/mysite3/user?action=joinForm" class="btn_s">회원가입</a></li>
		</ul>
	<%} %>
	
</div>
<!-- //header -->



