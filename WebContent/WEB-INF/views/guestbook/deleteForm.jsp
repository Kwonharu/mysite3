<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%
	int no = Integer.parseInt(request.getParameter("no"));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deleteForm</title>
</head>
<body>
	<form action="/mysite3/guest" method="get">
		<table>
			<tr>
				<td>비밀번호</td>
				<td><input type="text" name="password"></td>
				<td><button type="submit">삭제</button></td>
				<td><input type="hidden" name="action" value="delete"></td>
				<td><input type="hidden" name="no" value="<%=no%>"></td>
			</tr>
		</table>
	</form>
	<br><br>
	
	<a href="/mysite3/guest?action=addList">메인으로 돌아가기</a>
</body>
</html>