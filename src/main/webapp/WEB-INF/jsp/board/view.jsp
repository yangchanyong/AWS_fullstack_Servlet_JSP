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
        <h2>Board View</h2>
      </div>
      <div class="card-body">
 		<form method="post">
 			<div class="mb-3 mt-3">
  				<label for="title" class="form-label">title</label>
   				<input type="text" class="form-control" id="email" placeholder="Enter title" name="title" readonly value="${board.title}">
 			</div>
 			<div class="mb-3">
   	 			<label for="content" class="form-label">content</label>
   		 		<textarea class="form-control" id="content" placeholder="Enter content" name="content" rows="10" readonly>${board.content}</textarea>
  			</div>
  			<div class="mb-3">
    			<label for="writer" class="form-label">writer</label>
    			<input type="text" class="form-control" id="writer" placeholder="Enter writer" name="writer" value="${board.writer}" readonly>
  			</div>
  			<div class="mb-3">
    			<p class="form-label">files</p>
    			<c:forEach items="${board.attachs}" var="attach">
    				<p><a href="${pageContext.request.contextPath}/download?${attach.queryString}">${attach.origin}</a></p>
    			</c:forEach>
  			</div>
       		<div class="text-center">
       		 	<a href="modify?bno=${board.bno}&${cri.fullQueryString}" class="btn btn-warning">수정</a>
       		 	<a href="remove?bno=${board.bno}&${cri.fullQueryString}" class="btn btn-danger btn-remove">삭제</a>
       		 	<a href="list?${cri.fullQueryString}" class="btn btn-outline-primary">목록으로</a>
        	</div>
        	<div class="mb-3" id="replyArea">
            <p class="form-label mb-4 border-bottom pb-3"><i class="far fa-comment-dots"></i> replies</p>
            <div class="px-5 row mb-5">
              <textarea class="form-control mb-2" id="commentArea" placeholder="Enter comments" rows="3"></textarea>
              <button type="button" class="btn btn-primary opacity-75">댓글 작성</button>
            </div>
            <ul class="container replies">
              <li class="list-unstyled px-4">
                <div class="mb-4 small text-center">
                  <p>댓글이 없습니다</p>
                </div>
              </li>
            </ul>
          </div>
		</form>
      </div>
    </div>
  </main>
 <%@ include file="../common/footer.jsp" %>
 <script>
 	$(".btn-remove").click(function() {
 		return confirm("정말 삭제 하시겠습니까?");
 	});
	let contextPath = '${pageContext.request.contextPath}';
	let replyPath = contextPath + "/reply"
	let bno = '${board.bno}';
	// 댓글작성과 이어짐
	let writer = '${member.id}';
	
	// ajax를 function으로 묶어줘야 새로고침을 안함
	showList();
	function showList() {
		$.ajax({
			url : replyPath,
			data : {bno:bno},
			success : list
		});
	}
	
	// 댓글 목록
	function list(replies) {
    //  let jsonStr = '[{"rno":9,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":515}, {"rno":10,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":515}]'
    //  let replies = JSON.parse(jsonStr);
      console.log(replies);
      let str = "";
      
      if(!replies.length) {
    	str = `<li class="list-unstyled px-4">
            <div class="mb-4 small text-center">
            <p>댓글이 없습니다</p>
          </div>
        </li>`;
        $(".replies").html(str);
        return;
      }
      
      
      for(let i in replies) {
        let r = replies[i];
        console.log(r.rno, r.content, r.regDate, r.writer, r.bno);
        
        let isMine = writer === r.writer
        // let isMine = true;
        
        // el 문법에 escape처리 해야 적용됨
        str += `
        <li class="list-unstyled px-4" data-rno="\${r.rno}">
          <div class="row">
            <a class="text-muted small mb-3 col text-decoration-none" href="">
              <span class="fs-6 fw-bold">\${r.writer}</span>
              <span class="mx-5">\${r.regDate}</span>
              </a>
              <div class="col text-end">`;
          str += isMine ? '<a href="" class="text-danger"><i class="fas fa-times"></i></a>' : ''
          str += `</div>
              </div>
              <div class="mb-4 small">
                <p>\${r.content}</p>
                </div>
                </li>
        `;
      }
      // json 분석 (db에 저장된 댓글 출력)
      $(".replies").html(str);
    }

	// 댓글 작성
    $("#commentArea").next().click(function() {
      //console.log($("#commentArea").val()); //session 체크가 필요함
      let content = $("#commentArea").val();
      if(!writer) {
    	  alert("로그인 후 작성하세요");
    	  location.href = contextPath + "/member/login?href=" + encodeURIComponent(location.pathname + location.search + '#replyArea');
    	  return;
      }
      else if(!content) {
    	  alert("댓글 내용을 입력하세요");
    	  return;
      }
      // 비동기처리
      // console.log({bno:bno, content:content, writer:writer}); // 객체 확인용
      $.ajax ({
    	url : replyPath,
    	data : {bno:bno, content:content, writer:writer},
    	method : "POST",
    	success : function(data) {
    		alert("댓글이 성공적으로 작성되었습니다");
    		$("#commentArea").val("");
    		showList();
    	}
      })
    })


    // 댓글 삭제
    $(".replies").on("click", "li a.text-danger", function() {
      event.preventDefault();
      if(!confirm("댓글을 삭제하시겠습니까?")) {
    	  return false;
      }
      console.log($(this).closest("li").data("rno"));
      let rno = $(this).closest("li").data("rno");
      $.ajax ({
      	url : replyPath + "?rno=" + rno,
      	data : {rno:rno},
      	method : "DELETE",
      	success : function(data) {
      		// console.log(data)
      		if(data == 1) {
      			alert("댓글이 성공적으로 삭제되었습니다");
      			showList();
      		}
      	}
      })
    })
    
 </script>
</body>
</html>
      <!-- 게시판에 table을 안쓰는게 좋음 / ul, li 사용 -->