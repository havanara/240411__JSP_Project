<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Hello My First JSP Project</h1>
	<!-- 
		method get -> 주소표시줄에 ? 쿼리스트링을 달고 이동
		method post -> 별도의 저장 공간에 담아서 이동(보안, 많은 데이터 이동 시)
	 -->
	
	
	<!-- ne(아니면) eq(맞다면) -->
	<!-- 세션의 id가 null이라면(= id가 값이 있다면) -->
	<c:if test="${ses.id eq null }">
		<form action="/memb/login" method="post">
			id : <input type="text" name="id">
			pwd : <input type="password" name="pwd">
			<button type="submit">login</button>
		</form>
	</c:if>
	
	<!-- 세션의 id가 null이 아니라면(= id가 값이 없다면) -->
	<div>
		<c:if test="${ses.id ne null }">
			${ses.id }님이 login 하셨습니다. <br>
			계정 생성일 : ${ses.regdate } <br>
			최근 접속일 : ${ses.lastlogin } <br>
			<a href="/memb/modify?id=${ses.id }"><button>회원정보수정</button></a>
			<a href="/memb/list"><button>회원리스트</button></a>
			<a href="/memb/logout"><button>logout</button></a>
		</c:if>
	</div>
	
	<h3><a href="/brd/register">글쓰기 페이지로 ...</a></h3>
	<h3><a href="/brd/list">리스트 페이지로 ...</a></h3>
	<h3><a href="/memb/join">회원가입 페이지로 ...</a></h3>
	
	<script type="text/javascript">
		const msg_login = `<c:out value="${msg_login }"></c:out>`;
		console.log(msg_login);
		if(msg_login === '-1'){
			alert("로그인 정보가 일치하지 않습니다.");
		}
		
		const msg_update = `<c:out value = "${msg_update}" />`;
		console.log(msg_update);
		if(msg_update === 'ok'){
			alert("회원정보수정이 완료되었습니다. 다시 로그인하세요")
		}
		
		const msg_delete = `<c:out value = "${msg_delete}" />`;
		console.log(msg_delete);
		if(msg_delete === 'ok'){
			alert("회원 탈퇴가 완료되었습니다.")
		}
	</script>
</body>
</html>