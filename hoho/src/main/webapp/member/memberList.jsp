<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="domain.*"  %>  
<%@ page import="java.util.*" %>  
<%
	ArrayList<MemberVo> alist = (ArrayList<MemberVo>)request.getAttribute("alist");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#btn{
float: right;
margin-right: 40px;
}

#btn1{
margin-left: 30px;
}

body{
display:flex;
text-align:center;
justify-content: center;
align-items: center;

}

#main{
margin-top: 150px;
}
</style>


<!-- FONT AWE -->
<script src="https://kit.fontawesome.com/6c060c00b1.js" crossorigin="anonymous"></script>

<!-- BOOT STRAP -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</head>
<body>
  <div id="main">
        <h1 style="text-align: center;">회원 리스트</h1>
        <form name="frm" action="<%=request.getContextPath() %>/board/boardList.do" method="post">
        <table style="text-align: right;margin-top: 30px;">
        <tr>
        <td width="1000px;">
           <select name="searchType">
           <option value="subject">제목</option>
           <option value="writer">작성자</option>
           </select>
         </td>
         <td>
         <input type="text" name="keyword" size="20">
         </td>
         <td>
         <input type="submit" name="submit" value="검색">
         </td>
        </tr>
    </table> 
        </form>
        <hr><br>
        <br><br>
        <button type="button" class="btn btn-primary" id="btn1" onclick="location.href='<%=request.getContextPath()%>'">home</button>
        <table class="table table-hover" style="width: 1200px;" id="table1">
            <thead>
                <tr>
                    <th>회원번호</th>
                    <th>회원아이디</th>
                    <th>회원비밀번호</th> 
                    <th>회원휴대폰번호</th>                                       
                    <th>회원이름</th>
                    <th>회원이메일</th>                    
                    <th>가입일</th>
                    <th></th>
                </tr>
                <% for (MemberVo mv : alist){ %>
<tr>
<td><%=mv.getMidx() %></td>
<td><%=mv.getMemberid() %></td>
<td><%=mv.getMemeberpwd() %></td>
<td><%=mv.getMemberphone() %> </td>
<td><%=mv.getMembername()%></td>
<td><%=mv.getMemberemail() %>
<td><%=mv.getWriteday() %></td>
</tr>
<% } %>
           </thead>                      
</body>
</html>