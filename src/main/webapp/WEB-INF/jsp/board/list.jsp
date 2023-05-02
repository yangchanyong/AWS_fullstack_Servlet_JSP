<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        <h2>Board List</h2>
      </div>
      <div class="card-body">
        <div class="clearfix mb-3 list-header">
          <div class="float-start">
          <c:set var="amounts" value="5,10,25,50,100" />
          <select class="form-select amount">
          <c:forTokens items="${amounts}" delims="," var="amount">
            <option value="${amount}" ${page.cri.amount == amount ? 'selected' : ''}>${amount}개씩보기</option>
          </c:forTokens>
          </select>
          </div>
          <form>
          <input type="hidden" name="pageNum" value="1">
          <input type="hidden" name="amount" value="${page.cri.amount}">
          <input type="hidden" name="category" value="${page.cri.category}">
          <div class="float-start mx-5 row">
          <!-- 첫번째 파라미터 : 내가 보고싶은 배열 -->
			<div class="col">
				<input class="form-check-input" type="checkbox" id="check1" name="type" value="title" ${fn:contains(fn:join(page.cri.type, ','), 'title') ? 'checked' : ''}>
				<label class="form-check-label" for="check1">제목</label>
				<input class="form-check-input" type="checkbox" id="check2" name="type" value="content" ${fn:contains(fn:join(page.cri.type, ','), 'content') ? 'checked' : ''}>
				<label class="form-check-label" for="check2">내용</label>
				<input class="form-check-input" type="checkbox" id="check3" name="type" value="writer" ${fn:contains(fn:join(page.cri.type, ','), 'writer') ? 'checked' : ''}>
				<label class="form-check-label" for="check3">작성자</label>
			</div>
            <div class="input-group mb-3 col">
              <input type="text" class="form-control" placeholder="Search" name="keyword" value="${page.cri.keyword}">
              <button class="btn btn-success" type="submit"><i class="fas fa-search"></i></button>
            </div>
          </div>
          </form>
          <a href="write" class="float-end btn btn-primary">write</a>
        </div>
        <div class="card">
        
        <c:forEach var="board" items="${boards}" varStatus="stat">
          <a href="view?bno=${board.bno}&${page.cri.fullQueryString}" class="text-decoration-none">
            <div class="container card-body ${stat.last ? '': 'border-bottom'}">
              <div class="mb-4 text-truncate">${board.title} <span class="small text-muted">[${board.replyCnt}]</span></div>
              <div class="row text-muted small">
                <div class="col-7">${board.writer}</div>
                <div class="col text-end small"><span class="mx-3">${board.regdate}</span> <span>조회수 ${board.hitcount}</span></div>
              </div>
            </div>
          </a>
          </c:forEach>
          
       </div>
        <ul class="pagination mt-3 justify-content-center">
        <c:if test="${page.doublePrev}">
          <li class="page-item"><a class="page-link" href="list?pageNum=${page.startPage - 1}&${page.cri.queryString}"><i class="fas fa-angle-double-left"></i></a></li>
        </c:if>
        <c:if test="${page.prev}">
          <li class="page-item"><a class="page-link" href="list?pageNum=${page.cri.pageNum - 1}&${page.cri.queryString}"><i class="fas fa-angle-left"></i></a></li>
        </c:if>
        
        <c:forEach begin="${page.startPage}" end="${page.endPage}" var="i">
          <li class="page-item"><a class="page-link ${page.cri.pageNum == i ? 'active' : ''}" href="list?pageNum=${i}&${page.cri.queryString}">${i}</a></li>
		</c:forEach>
		
        <c:if test="${page.next}">
          <li class="page-item"><a class="page-link" href="list?pageNum=${page.cri.pageNum + 1}&amount=${page.cri.amount}"><i class="fas fa-angle-right"></i></a></li>
        </c:if>
        <c:if test="${page.doubleNext}">
          <li class="page-item"><a class="page-link" href="list?pageNum=${page.endPage + 1}&amount=${page.cri.amount}"><i class="fas fa-angle-double-right"></i></a></li>
        </c:if>
        </ul>
      </div>
    </div>
  </main>
 <%@ include file="../common/footer.jsp" %>
</body>
<script>
	$(".amount").change(function() {
		// console.log($(this).val());
		
		let page = '${page.cri.pageNum}'; // 텍스트 형태로 js가 인식
		let amount = $(this).val();
		let category = '${page.cri.category}';
		// amount를 뺀 나머지 문자열이 필요
		let type = '${page.cri.typeStr}'
		
		let obj = {pageNum:page, amount:amount, category:category};
		location.search = $.param(obj) + type;
	}) 
	
	// 게시글 검색시 검색어가 없거나 체크박스에 아무것도 체크되어있지 않을때 이벤트
	$(".list-header :checkbox:checked").length ? '' : $(".list-header :checkbox:eq(0)").prop("checked", true);
	$(".list-header form").submit(function () {
		// alert($(this).find(":checkbox:checked").length);
		if(!$(this).find(":checkbox:checked").length || !$(this).find(":text").val().trim()) { // trim은 문자열 길이가 0일때 false
			alert("검색 타입을 지정하고 검색어를 입력하세요");
			return false;
		}
		// || $(this).find(":text").val().trim()
		// event.preventDefault();
	});
</script>
</html>
      <!-- 게시판에 table을 안쓰는게 좋음 / ul, li 사용 -->