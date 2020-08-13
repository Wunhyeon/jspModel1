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
	String inputPassword = request.getParameter("password");
%>

<jsp:useBean id="bean" class= "model.BoardBean">
	<jsp:setProperty name="bean" property="*"/>
</jsp:useBean>

<%
	String realPassword = bdao.getPassword(num);
	if(inputPassword.equals(realPassword)){
		bdao.deleteOne(num);
		response.sendRedirect("BoardList.jsp");
	}else{
%>
	<script>
		alert("비밀번호가 맞지 않습니다.");
		history.go(-1);
	</script>
<%
	}
%>
</body>
</html>