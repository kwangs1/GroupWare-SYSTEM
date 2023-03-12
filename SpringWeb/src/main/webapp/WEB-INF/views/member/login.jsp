<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<style>
.container {
  display: flex; /* 요소들을 가로 방향으로 나열하기 위해 flexbox 레이아웃 사용 */
  flex-direction: column; /* 요소들을 세로 방향으로 나열 */
  align-items: center; /* 요소들을 수직 중앙 정렬 */
  justify-content: center; /* 요소들을 수평 중앙 정렬 */
  height: 100vh; /* 화면 전체를 차지하도록 높이 설정 */
}

.container > div {
  margin-bottom: 10px; /* 요소들 간 간격 설정 */
  width: 300px; /* 요소의 너비 설정 */
}
.container > div > input {
  width: 250px;
  hight: 400px;
}

</style>
<body>
<form name="frm" method="post">
<div class="container">
	<h1>로그인</h1>
	<div>
		<input type="text" name="id" placeholder="아이디" />
	</div>
	
	<div>
		<input type="password" name="pw" placeholder="비밀번호" />	
	</div>
	
	<div>
		<button type="submit">로그인</button>	
	</div>
</div>
</form>
</body>
</html>