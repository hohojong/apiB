<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>1.클라이언트와 서버의 환경정보 일기</h2>
<a href="request.jsp?one=1&two=2">Get 방식 전송</a>

<br>


<form action="request.jsp" method="post">
일번 : <input type="text" name="one" value=""><br>
이번 : <input type="text" name="two" value=""><br>
삼번 : <input type="text" name="three" value=""><br>
사번 : <input type="text" name="four" value=""><br>
오번 : <input type="text" name="five" value=""><br>
육번 : <input type="text" name="six" value=""><br>
<input type="submit" value="post 방식 전송"> 
</form>





</body>
</html>