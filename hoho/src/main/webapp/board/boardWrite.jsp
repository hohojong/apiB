<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
</head>
<body>
<body>

		<div class="container" role="main" style="width: 700px;padding-top: 100px;">

			<h2>게시판 글쓰기</h2>

			<form name="form" action="<%=request.getContextPath()%>/board/boardWriteAction.do"method="post" enctype="multipart/form-data">

				<div class="mb-3">

					<label for="title">제목</label>

					<input type="text" class="form-control" name="subject" id="title" placeholder="제목을 입력해 주세요">

				</div>

				

				<div class="mb-3">

					<label for="reg_id">작성자</label>

					<input type="text" class="form-control" name="writer" id="reg_id" placeholder="이름을 입력해 주세요">

				</div>

				

				<div class="mb-3">

					<label for="content">내용</label>

					<textarea class="form-control" rows="15" name="content" id="content" placeholder="내용을 입력해 주세요" ></textarea>

				</div>
		

			<div class="mb-3">

					<label for="reg_id">파일</label>

					<input type="file" class="form-control" name="filename" id="filename" >

				</div>
				

			

			<div style="justify-content: center;display: flex;">

				<button type="submit" class="btn btn-sm btn-primary" id="btnSave">저장</button>

				<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>

			</div>

		</div>

</form>

</body>

</html>



</body>
</html>