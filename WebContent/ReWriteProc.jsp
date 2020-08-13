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
	BoardDAO bdao = new BoardDAO();
%>

<jsp:useBean id="bean" class="model.BoardBean">
	<jsp:setProperty name="bean" property="*"/>
</jsp:useBean>

<%
	bdao.reWrite(bean);
	response.sendRedirect("BoardList.jsp");
%>


</body>
</html>