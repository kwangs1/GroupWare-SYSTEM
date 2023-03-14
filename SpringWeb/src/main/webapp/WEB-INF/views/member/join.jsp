<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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

<!-- 유효성 검사 -->
<style>
.id_input_re_1 {
	color: green;
	display: none;
}
.id_input_re_2 {
	color: red;
	display: none;
}

.name_input_re_1 {
	color: green;
	display: none;
}
.name_input_re_2 {
	color: red;
	display: none;
}
.mail_input_re_1 {
	color: red;
	display: none;
}
</style>
</head>
<body>
<form name = 'frm' method = 'post'>
<input type="hidden" name='sabun' />
<div class="container">
	<h1>어서오세요! 회원가입 페이지 입니다.</h1>
	<div>
		<input type='text' name="id" class="id_input" placeholder="아이디"/>
		 <span class="id_input_re_1">사용 가능한 아이디입니다.</span>
		 <span class="id_input_re_2">아이디가 이미 존재합니다.</span>
	</div>
	
	<div>
		<input type='password' name="pw" placeholder="비밀번호"/>
	</div>
	
	<div>
		<input type='text' name="name" class="name_input" placeholder="이름"/>
		 <span class="name_input_re_1">사용 가능합니다.</span>
		 <span class="name_input_re_2">중복된 이름이 있습니다.</span>
	</div>	
	
	<div>
		<input type='text' name="email" class="mail_input" placeholder="이메일"/>
		<span class="mail_input_box_warn"></span>
		<div class="mail_check_button">전송</div>
	</div>
	
	<div>
		<a href="javascript:execDaumPostcode()" >우편번호검색</a>
		<input type='text' name="zipcode" id="zipcode" placeholder="우편번호"/>
	</div>
	
	<div>
		<input type='text' name="roadAddress" id="roadAddress" placeholder="도로명주소"/>
	</div>
	
	<div>
		<input type='text' name="jibunAddress" id="jibunAddress" placeholder="지번주소"/>
	</div>
	
	<div>
		<input type='text' name="namujiAddress" id="namujiAddress" placeholder="나머지주소"/>
	</div>
	<span id="guide" style="color: #999"></span>
	<button onclick='join()'>가입</button>
</div>
</form>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function join(){
	submit();
}

var idCheck = false;
var nameCheck = false;
var mailCheck = false;

//id 중복검사 ajax
$('.id_input').on('propertychange change keyup paste input',function (){
    var id = $('.id_input').val();
    
    if (id === '') { // 입력값이 비어있는 경우
        $('.id_input_re_1').css('display', 'none');
        $('.id_input_re_2').css('display', 'none');
        idCheck = false; // 중복 검사 결과 초기화
        return; // 중복 검사를 수행하지 않고 종료
    }
    
    $.ajax({
        type : 'post',
        url : '${path}/member/idcheck',
        data : {id : id},
        success : function(result){ // 이 부분도 수정
            if(result == 'success'){
                $('.id_input_re_1').css('display','block');
                $('.id_input_re_2').css('display','none');
                idCheck = true;
            }else{
                $('.id_input_re_2').css('display','block');
                $('.id_input_re_1').css('display','none');
                idCheck = false;
            }       
        },//end success
        error : function(error){
            console.log(error);
        }
        
    });//end ajax
});

//이름 검사 ajax
$('.name_input').on('propertychange change keyup paste input',function(){
	var name = $('.name_input').val();
	
	if(name === ''){
        $('.name_input_re_1').css('display', 'none');
        $('.name_input_re_2').css('display', 'none');
        nameCheck = false;
        return;
	}
	
	$.ajax({
		type: 'post',
		url: '${path}/member/namecheck',
		data: {name : name},
		success: function(result){
			if(result == 'success'){
		        $('.name_input_re_1').css('display', 'block');
		        $('.name_input_re_2').css('display', 'none');
		        nameCheck = true;
			}else{
		        $('.name_input_re_1').css('display', 'none');
		        $('.name_input_re_2').css('display', 'block');
		        nameCheck = false;
			}
		},//end success
		error: function(error){
			console.log(error);
		}
	});//end ajax
});

//이메일 전송 ajax
var code = '';
//email 정규표현식
function mailFormCheck(email){
	 var mailExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	 return mailExp.test(email);
}

$('.mail_input').on('propertychange change keyup paste input',function(){
	var email = $('.mail_input').val();
	var warnMsg = $('.mail_input_box_warn');
	
	if(email === ''){
		warnMsg.html("");
	    warnMsg.attr('hidden', true);
	    mailCheck = false;
	    return;
	}
});


$('.mail_check_button').click(function(){
	var email = $('.mail_input').val();
	var warnMsg = $('.mail_input_box_warn');
	
	if(mailFormCheck(email)){
		warnMsg.html("이메일이 전송 되었습니다.");
		warnMsg.css("display","inline-block");
		warnMsg.css("color","green");
	}else{
		warnMsg.html("이메일 형식이 올바르지 않습니다.");
		warnMsg.css("display",'inline-block');
		warnMsg.css("color",'red');
		return false;
	}
	

	$.ajax({
		type: 'post',
		url: '${path}/member/mailcheck?email='+email,
		success: function(data){
			code = data;
		}
	});//end ajax
	
});
</script>


<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
<!-- 다음 주소 -->
/* 우편번호 검색API */
function execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function(data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
      var extraRoadAddr = ''; // 도로명 조합형 주소 변수

      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
      if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
        extraRoadAddr += data.bname;
      }
      // 건물명이 있고, 공동주택일 경우 추가한다.
      if(data.buildingName !== '' && data.apartment === 'Y'){
        extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
      }
      // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
      if(extraRoadAddr !== ''){
        extraRoadAddr = ' (' + extraRoadAddr + ')';
      }
      // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
      if(fullRoadAddr !== ''){
        fullRoadAddr += extraRoadAddr;
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
      document.getElementById('roadAddress').value = fullRoadAddr;
      document.getElementById('jibunAddress').value = data.jibunAddress;

      // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
      if(data.autoRoadAddress) {
        //예상되는 도로명 주소에 조합형 주소를 추가한다.
        var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
        document.getElementById('guide').innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';

      } else if(data.autoJibunAddress) {
          var expJibunAddr = data.autoJibunAddress;
          document.getElementById('guide').innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
      } else {
          document.getElementById('guide').innerHTML = '';
      }  
    }
  }).open();

}
</script>
</body>
</html>