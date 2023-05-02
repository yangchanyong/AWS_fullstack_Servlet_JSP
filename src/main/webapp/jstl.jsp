<%@page import="java.util.ArrayList"%>
<%@page import="com.chanyongyang.jsp.domain.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>2023. 3. 9.오전 10:16:19</title>
	<meta name='viewport' content='width=device-width, initial-scale=1'>
</head>
<body>

<%
	List<Board> boards = new ArrayList<>();
	boards.add(new Board("aaa", "bbb", "ccc"));
	boards.add(new Board("aaa1", "bbb", "ccc"));
	boards.add(new Board("aaa2", "bbb", "ccc"));
	
	request.setAttribute("boards", boards);
%>

	<!-- scope의 기본값은 page, set을 통한 지정, out을 통한 출력 -->
	<c:set var="num" value="15" />
	<c:set var="title" value="<script>alert()</script>" />
	<h2>${num}</h2>
	
	<!-- 조금 더 합리적인 방법의 호출 
		 c:out은 xss의 공격을 방지함 -->
	<h2><c:out value="안녕하세요" /></h2>
	<h2><c:out value="${num}" /></h2>
	
	<!-- xss차단 불가 스크립트로 해석 -->
	<%-- <h2>${title}</h2> --%>
	
	<!-- 문자열로 해석하여 xss를 원천차단함 -->
	<h2><c:out value="${title}" /> </h2>
	
	<!-- 조건문1 -->
	<c:if test="${num == 20}">
		<h2>20 입니다</h2>	
	</c:if>
	<c:if test="${num != 20}" >
		<h2>20이 아닙니다</h2>
	</c:if>
	
	<!-- 조건문2 복합적인 형태에 사용 -->
	<c:choose>
		<c:when test="${num == 20}">
			<h3>20 입니다</h3>
		</c:when>
		<c:when test="${num == 30}">
			<h3>30 입니다</h3>
		</c:when>
		<c:otherwise>
			<h3>20도 30도 아닙니다</h3>
		</c:otherwise>
	</c:choose>
	
	<!-- 반복문(for와 같음 일반포문, 향상포문) 필수속성 : 1.  
		 begin(이상) end(이하) step(건너뜀)-->
	<c:forEach var="i" begin="1" end="10" step="3">
		<h4>반복문장 ${i}</h4>
	</c:forEach>
	
	<h2>forEach를 활용한 구구단</h2>
	<c:forEach var="i" begin="2" end="9">
		<c:forEach var="j" begin="1" end="9" >
			<h4>${i}*${j} = ${i*j}</h4>
		</c:forEach>
	</c:forEach>
	
	<!-- items는 향상포문을 쓰겠다는것이며, 순회대상이 된다.
		 step 사용가능
		 varStatus는 index와 count를 호출할 수 있음 -->
	<c:forEach var="board" items="${boards}" varStatus="stat" step="2">
		<h4>${stat.index}</h4>
		<h4>${stat.count}</h4>
		<h3>${board.title}</h3>
	</c:forEach>
	
	<!-- c:import(잘 안씀)는 인클루드랑 비슷함
	 	 인클루드의 대상에 추가 파라미터를 줄 때 사용 c:param이랑 같이 사용 -->
	
	<!-- c:catch는 예외처리 (잘안씀) -->
</body>
</html>