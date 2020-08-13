<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>게시글 쓰기</h2>

	

<form action = "BoardWriteProc.jsp" method="post">
	<table>
		<tr>
			<td>
				작성자
			</td>
			<td>
				<input type="text" name="writer">
			</td>
		</tr>
		<tr>
			<td>
				제목
			</td>
			<td>
				<input type="text" name="subject">
			</td>
		</tr>
		<tr>
			<td>
				Email
			</td>
			<td>
				<input type="email" name="email">
			</td>
		</tr>
		<tr>
			<td>
				Password
			</td>
			<td>
				<input type="password" name="password">
			</td>
		</tr>
		<tr>
			<td>
				Content
			</td>
			<td>
				<textarea name="content">
				</textarea>
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="글쓰기">
				<input type="reset" value="reset">
				<button onclick="location.href='BoardList.jsp'">전체 게시글 보기</button>
			</td>
		</tr>
	
	</table>

</form>
</body>
</html>