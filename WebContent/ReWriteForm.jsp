<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.BoardBean" %>
<%@ page import="model.BoardDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num").trim());
		int ref = Integer.parseInt(request.getParameter("ref").trim());
		int re_step = Integer.parseInt(request.getParameter("re_step").trim());
		int re_level = Integer.parseInt(request.getParameter("re_level").trim());
	%>
	
	<jsp:useBean id="bean" class="model.BoardBean">
		<jsp:setProperty name="bean" property="*"/>
	</jsp:useBean>
	
	<%
		BoardDAO bdao = new BoardDAO();
		bean = bdao.boardInfo(num);
	%>
	
	<h2><%=bean.getSubject() %>의 답글</h2>
	<form action="ReWriteProc.jsp" method="post">
	<table>
		<tr>
			<td>Writer</td>
			<td><input type="text" name="writer"></td>
		</tr>
		<tr>
			<td>Subject</td>
			<td><input type="text" name="subject"></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><input type="email" name="email"></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td>Content</td>
			<td>
				<textarea rows="" cols=""></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="답글쓰기완료">
				<input type="reset" value="reset">
				<button type="button" onclick="location.href='BoardList.jsp'">전체 게시글 보기</button>
			</td>
		</tr>
	
	</table>
	<input type="hidden" name="num" value="<%=num %>">
	<input type="hidden" name="ref" value="<%=ref %>">
	<input type="hidden" name="re_step" value="<%=re_step %>">
	<input type="hidden" name="re_level" value="<%=re_level %>">
	</form>
	

</body>
</html>