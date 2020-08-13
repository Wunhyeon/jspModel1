<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.BoardBean" %>
<%@ page import= "model.BoardDAO" %>
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
		BoardDAO bdao = new BoardDAO();
	%>
	
	<jsp:useBean id="bean" class="model.BoardBean">
		<jsp:setProperty name="bean" property="*"/>
	</jsp:useBean>
	
	<%
		bean = bdao.boardInfo(num);
	%>
	
	<h2>게시글보기</h2>
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
			<td colspan="4">
				<button onclick="location.href='ReWriteForm.jsp?num=<%=bean.getNum()%>&ref=<%=bean.getRef()%>&re_step=<%=bean.getRe_step()%>&re_level=<%=bean.getRe_level()%>'">답글쓰기</button>
				<button onclick="location.href='UpdateForm.jsp?num=<%=bean.getNum()%>'">수정하기</button>
				<button onclick="location.href='DeleteForm.jsp?num=<%=bean.getNum()%>'">삭제하기</button>
				<button onclick="location.href='BoardList.jsp'">목록보기</button>
			</td>
		</tr>
	</table>
</body>
</html>