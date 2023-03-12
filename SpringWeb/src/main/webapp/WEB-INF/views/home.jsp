<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="path" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>Home</title>
</head>
<body>
<c:if test="${member == null }">
<h1>
	어서오세요😀
</h1>
<a href='${path}/member/join' onclick= 'join()'>회원가입</a>
<a href='${path}/member/login' onclick= 'join()'>로그인</a>
</c:if>

<c:if test="${member != null }">
<h1>
	${member.name}님😎 환영합니다!
</h1>
<a href='${path}/member/logout' onclick= 'join()'>로그아웃</a>
</c:if>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

//세션 유지 확인 주기
const sessionCheckInterval = 61000;

// 세션 유지 확인 함수
function checkSession() {
  // Ajax 요청을 보내어 응답을 받아옴
  $.ajax({
    url: '/member/session-check',
    success: function(data) {
      // 세션 유지 확인 성공
      console.log('Session is still alive.');
    },
    error: function(xhr, status, error) {
      // 세션 유지 확인 실패 (세션 타임아웃 발생)
      console.log('Session is expired.');
      // 경고창을 띄움
      alert('세션이 만료되었습니다. 다시 로그인해주세요.');
      // 로그인 페이지로 이동
      window.location.href = '${path}/member/login';
    }
  });
}

// 세션 유지 확인을 주기적으로 수행
setInterval(checkSession, sessionCheckInterval);

</script>
</body>
</html>
