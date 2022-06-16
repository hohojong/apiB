<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="domain.BoardVo" %>    
<%

String bidx = (String)request.getAttribute("bidx");

%>    
    
<!DOCTYPE htl>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">

#btnSave{
margin-right: 10px;
}

</style>


<!-- FONT AWE -->
<script src="https://kit.fontawesome.com/6c060c00b1.js" crossorigin="anonymous"></script>

<!-- BOOT STRAP -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<style>
#btnList{
margin-right: 10px; 


}





</style>




</head>
<body>
<body>
<form action="<%=request.getContextPath()%>/board/boardDeleteAction.do">
<input type="hidden" name="bidx" value="<%=bidx%>">
<div style="text-align: center; margin-top: 100px;">	
<tr style="text-align:center"; display>
<td colspan=2  style="width:100px;">삭제하시겠습니까?</td>
			
</div>
			

			<div style="text-align: center; margin-top: 100px;">

				<button type="submit" class="btn btn-sm btn-primary" id="btnList" >삭제</button>

				<button type="button" class="btn btn-sm btn-primary" id="btnList"  onclick="location.href='<%=request.getContextPath()%>/board/boardList.do?bidx=<%=bidx%>'">목록</button>
				
			
 
			</div>

		</div>

</form>

</body>

</html>



</body>
</html>