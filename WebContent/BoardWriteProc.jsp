<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.BoardDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		request.setCharacterEncoding("utf-8");
	%>
	
	<jsp:useBean id="bean" class="model.BoardBean">
		<jsp:setProperty name="bean" property="*"/>
	</jsp:useBean>
	
	<%
		BoardDAO bdao = new BoardDAO();
		bdao.writeBoard(bean);
		
		//게시글 저장후 전체 게시글 보기
		response.sendRedirect("BoardList.jsp");
	%>


</body>
</html>