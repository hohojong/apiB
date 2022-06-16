<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <%
    
    
    request.setCharacterEncoding("UTF-8");
    
    
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<H2>데이터 받기</H2>
<ul>
<li><%=request.getParameter("one") %></li>
<li><%=request.getParameter("two") %></li>
<li><%=request.getParameter("three")%></li>
<li><%=request.getParameter("four") %></li>
<li><%=request.getParameter("five") %></li>
<li><%=request.getParameter("six") %></li>
</ul>
</body>
</html>