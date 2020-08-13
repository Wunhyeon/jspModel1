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
	int num = Integer.parseInt(request.getParameter("num"));
	BoardDAO bdao = new BoardDAO();
%>

<jsp:useBean id="bean" class="model.BoardBean">
	<jsp:setProperty name="bean" property="*"/>
</jsp:useBean>

<%
	bean = bdao.boardInfo(num);
	int re_step = bean.getRe_step();
	String subject = bean.getSubject();
	
	System.out.println(subject.substring(0, 4));
	
	if(subject.substring(0, 4).equals("[수정]")){
		subject = subject.substring(4);
		System.out.println("수정");
	}
	
	if(re_step >= 2){
		subject = subject.substring(4*(re_step-1));
		System.out.println("[re]");
	}
	
%>

<h2>Update Form</h2>
	<form action="BoardUpdateProc.jsp" method = "post">
	<table border="1">
		<tr>
			<td>글번호</td><td><%=num %></td><td>조회수</td><td><%=bean.getReadcount() %></td>
		</tr>
		<tr>
			<td>작성자</td><td><input type="text" name="writer" value ="<%=bean.getWriter() %>"></td><td>작성일</td><td><%=bean.getReg_date() %></td>
		</tr>
		<tr>
			<td>이메일</td><td colspan="3"><input type="eamil" name="email" value="<%=bean.getEmail() %>"></td>
		</tr>
		<tr>
			<td>제목</td><td colspan="3"><input type="text" name="subject" value="<%=subject %>" ></td>
		</tr>
		<tr>
			<td>글내용</td><td colspan="3"><%=bean.getContent() %></td>
		</tr>
		<tr>
			<td colspan="4">
				<button type="submit">Update</button>
				<button onclick="location.href='BoardList.jsp'">목록보기</button>
			</td>
		</tr>
	</table>
	<input type="hidden" name="num" value="<%=bean.getNum() %>">
	<input type="hidden" name="ref" value="<%=bean.getRef() %>">
	<input type="hidden" name="re_step" value="<%=bean.getRe_step() %>">
	<input type="hidden" name="re_level" value="<%=bean.getRe_level() %>">
	<input type="hidden" name="readCount" value="<%=bean.getReadcount() %>">
	</form>

</body>
</html>