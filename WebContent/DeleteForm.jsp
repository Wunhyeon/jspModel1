<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.BoardBean" %>
<%@ page import = "model.BoardDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("utf-8");
	BoardDAO bdao = new BoardDAO();
	int num = Integer.parseInt(request.getParameter("num"));
%>

<jsp:useBean id="bean" class="model.BoardBean">
	<jsp:setProperty name="bean" property="*"/>
</jsp:useBean>

<%
	bean = bdao.boardInfo(num);

%>

	<h2>Delete Table</h2>
	<form action="deleteProc.jsp" method = "post">
	<table border="1">
		<tr>
			<td>글번호</td><td><%=num %></td><td>조회수</td><td><%=bean.getReadcount() %></td>
		</tr>
		<tr>
			<td>작성자</td><td><%=bean.getWriter() %></td><td>작성일</td><td><%=bean.getReg_date() %></td>
		</tr>
		<tr>
			<td>이메일</td><td colspan="3"><%=bean.getEmail() %></td>
		</tr>
		<tr>
			<td>제목</td><td colspan="3"><%=bean.getSubject() %></td>
		</tr>
		<tr>
			<td>글내용</td><td colspan="3"><%=bean.getContent() %></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="delete"></td>
		</tr>
	</table>
	<input type="hidden" name="num" value="<%=num %>">
	</form>
</body>
</html>