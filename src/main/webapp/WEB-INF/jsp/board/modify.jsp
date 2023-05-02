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
        <h2>Board Modify</h2>
      </div>
      <div class="card-body">
 		<form method="post">
 			<div class="mb-3 mt-3">
  				<label for="title" class="form-label">title</label>
   				<input type="text" class="form-control" id="email" placeholder="Enter title" name="title"  value="${board.title}">
 			</div>
 			<div class="mb-3">
   	 			<label for="content" class="form-label">content</label>
   		 		<textarea class="form-control" id="content" placeholder="Enter content" name="content" rows="10" >${board.content}</textarea>
  			</div>
  			<div class="mb-3">
    			<label for="writer" class="form-label">writer</label>
    			<input type="text" class="form-control" id="writer" placeholder="Enter writer" name="writer" value="${board.writer}" readonly>
  			</div>
       		<div class="text-center">
       		<input type="hidden" name="bno" value="${board.bno}">
       		<input type="hidden" name="pageNum" value="${cri.pageNum}">
       		<input type="hidden" name="amount" value="${cri.amount}">
       		<input type="hidden" name="category" value="${cri.category}">
       		<%-- <p>${cri}</p> --%>
       		<c:if test="${not empty cri.type}">
       			<c:forEach items="${cri.type}" var="type">
       				<input type="hidden" name="type" value="${type}">
       			</c:forEach>
       			<input type="hidden" name="keyword" value="${cri.keyword}">
       		</c:if>
       		 	<button class="btn btn-warning" formaction="modify">수정</button>
       		 	<a href="list?${cri.fullQueryString}" class="btn btn-outline-warning">목록</a>
        	</div>
		</form>
      </div>
    </div>
  </main>
 <%@ include file="../common/footer.jsp" %>
</body>
</html>
      <!-- 게시판에 table을 안쓰는게 좋음 / ul, li 사용 -->