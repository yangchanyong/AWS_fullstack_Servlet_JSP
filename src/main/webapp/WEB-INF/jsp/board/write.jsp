<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/head.jsp" %>
</head>
<body>
<%@ include file="../common/header.jsp" %>
 <main class="container p-3 pb-5">
    <div class="card">
      <div class="card-header p-5">
        <h2>Board Write</h2>
      </div>
      <div class="card-body">
 		<form method="post" enctype="multipart/form-data">
 			<div class="mb-3 mt-3">
  				<label for="title" class="form-label">title</label>
   				<input type="text" class="form-control" id="email" placeholder="Enter title" name="title">
 			</div>
 			<div class="mb-3">
   	 			<label for="content" class="form-label">content</label>
   		 		<textarea class="form-control" id="content" placeholder="Enter content" name="content" rows="10"></textarea>
  			</div>
  			<div class="mb-3">
    			<label for="writer" class="form-label">writer</label>
    			<input type="text" class="form-control" id="writer" placeholder="Enter writer" name="writer" value="${member.id}" readonly>
  			</div>
  			<div class="mb-3 mt-3">
  				<label for="file" class="form-label">files</label>
   				<input type="file" class="form-control" id="file" name="file" multiple>
 			</div>
       		<div class="text-center">
       		 	<button class="btn btn-primary">글쓰기</button>
       		 	<a href="javascript:history.back()" class="btn btn-outline-primary">목록으로</a>
        	</div>
		</form>
      </div>
    </div>
  </main>
 <%@ include file="../common/footer.jsp" %>
</body>
</html>
      <!-- 게시판에 table을 안쓰는게 좋음 / ul, li 사용 -->