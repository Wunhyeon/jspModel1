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
		BoardDAO bdao = new BoardDAO();
	%>
	
	<jsp:useBean id="bean" class="model.BoardBean">
		<jsp:setProperty name="bean" property="*"/>
	</jsp:useBean>
	
	<%
		bdao.update(bean);
		response.sendRedirect("BoardList.jsp");
	%>
	
	
</body>
</html>