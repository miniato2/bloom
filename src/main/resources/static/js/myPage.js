

document.addEventListener("DOMContentLoaded", function () {
    // elements
    var modalBtn = document.getElementById("modalBtn");
    var modal = document.getElementById("myModal");
    var closeBtn = document.getElementById("closeBtn");


    function toggleModal() {
        modal.classList.toggle("show");
    }

    // functions


    // events
    modalBtn.addEventListener("click", toggleModal);
    modalBtn.addEventListener("click",sendEmail);
    closeBtn.addEventListener("click", toggleModal);

    // window.addEventListener("click", function (event) {
    //     // 모달의 검은색 배경 부분이 클릭된 경우 닫히도록 하는 코드
    //     if (event.target === modal) {
    //         toggleModal();
    //     }
    // });
});

function sendEmail(){
    $.ajax({
        url: "/mypage/email",
        type: 'GET',
        data : {

        },
        success:function(data,status,xhr){
          console.log('인증번호 발송 완료');

        },
        error: function(xhr,status,error) {
            console(error);
        }
    });

}

function codeSend(){
    var modal = document.getElementById("myModal");

    const code =$("#authCode").val();

    $.ajax({
        url: "/mypage/verify",
        type: 'POST',
        data : {
            code : code,
        },
        success:function(data,status,xhr){
            if(data.status==="success"){

                alert(data.message);
                activatePW();
                modal.classList.toggle("show");




            }else{
                alert(data.message);
            }

        },
        error: function(xhr,status,error) {
            alert("입력코드 발송 실패")
            console(error);
        }
    });
}

function activatePW(){
    // 'nickname' id를 가진 요소를 찾습니다.
    var passwordInput = document.getElementById('pw');

    // 해당 요소의 disabled 속성을 false로 설정해 활성화합니다.
    passwordInput.disabled = false;

    // 선택적으로, 사용자가 입력을 시작할 수 있도록 해당 필드에 포커스를 줍니다.
    passwordInput.focus();

}
function sleepPW(){

    var passwordInput = document.getElementById('pw');


    passwordInput.disabled = true;


}







function activateNickname(){
    // 'nickname' id를 가진 요소를 찾습니다.
    var nicknameInput = document.getElementById('nickname');

    // 해당 요소의 disabled 속성을 false로 설정해 활성화합니다.
    nicknameInput.disabled = false;

    // 선택적으로, 사용자가 입력을 시작할 수 있도록 해당 필드에 포커스를 줍니다.
    nicknameInput.focus();

}
function sleepNickname(){
    // 'nickname' id를 가진 요소를 찾습니다.
    var nicknameInput = document.getElementById('nickname');

    // 해당 요소의 disabled 속성을 false로 설정해 활성화합니다.
    nicknameInput.disabled = true;

    // 선택적으로, 사용자가 입력을 시작할 수 있도록 해당 필드에 포커스를 줍니다.


}

function nicknameCheck(nickname){
    nickname_regex =  /^[가-힣a-zA-Z0-9]{3,20}$/;



    if(nickname_regex.test(nickname)){
        return true;
    }else{
        return false;
    }

}

function updateNickname() {
    let nicknameInput = document.getElementById('nickname');
    let nickname = nicknameInput.value;
    let nicknameResult = document.getElementById('nicknameResult');


    if (nicknameCheck(nickname)) {

        nicknameResult.innerHTML='사용가능 닉네임 입니다.';

        $.ajax({
            url: "/mypage/updateNickname",
            type: 'POST',
            data: {
                nickname: nickname
            },
            success: function (data, status, xhr) {
                console.log(data);
                if (data.status === "success") {
                    nicknameResult.innerHTML='닉네임 수정 완료';
                    sleepNickname();



                } else {
                    nicknameResult.innerHTML='닉네임 수정 실패';
                   return;
                }

            },
            error: function (xhr, status, error) {
                console(error);
            }

        });



    } else {

        nicknameResult.innerHTML='사용불가 닉네임 입니다.';


    }
}



function activatePhone(){
    // 'nickname' id를 가진 요소를 찾습니다.
    var phoneInput = document.getElementById('phone');

    // 해당 요소의 disabled 속성을 false로 설정해 활성화합니다.
    phoneInput.disabled = false;

    // 선택적으로, 사용자가 입력을 시작할 수 있도록 해당 필드에 포커스를 줍니다.
    phoneInput.focus();

}
function sleepPhone(){

    var phoneInput = document.getElementById('phone');


    phoneInput.disabled = true;



}

function phoneCheck(phone){
    phone_regex1 = /^010[0-9]{8}$/;


    if(phone_regex1.test(phone)){
        return true;
    }else{
        return false;
    }
}

function updatePhone() {
    let phoneInput = document.getElementById('phone');
    let phone = phoneInput.value;
    let phoneResult = document.getElementById('phoneResult');


    if (phoneCheck(phone)) {

        phoneResult.innerHTML='사용가능 전화번호 입니다.';

        $.ajax({
            url: "/mypage/updatePhone",
            type: 'POST',
            data: {
                phone : phone
            },
            success: function (data, status, xhr) {
                console.log(data);
                if (data.status === "success") {
                    phoneResult.innerHTML='전화번호 수정 완료';
                    sleepPhone();



                } else {
                    phoneResult.innerHTML='전화번호 수정 실패';
                    return;
                }

            },
            error: function (xhr, status, error) {
                console(error);
            }

        });

    } else {

        phoneResult.innerHTML='사용불가 전화번호 입니다.';
    }
}

function PWCheck(pw){
    pw_regex =  /^[0-9a-zA-Z]{12,15}$/;



    if(pw_regex.test(pw)){
        return true;
    }else{
        return false;
    }

}

function updatePW() {
    let pwInput = document.getElementById('pw');
    let pwResult = document.getElementById('pwResult')


    let pw = pwInput.value;

    if (PWCheck(pw)) {

        pwResult.innerHTML = '유효한 비밀번호 입니다.';

        $.ajax({
            url: "/mypage/updatePW",
            type: 'POST',
            data: {
                pw : pw
            },
            success: function (data, status, xhr) {
                console.log(data);
                if (data.status === "success") {
                    pwResult.innerHTML='비밀번호 수정 완료';
                    sleepPW();



                } else {
                    pwResult.innerHTML='전화번호 수정 실패';
                    return;
                }

            },
            error: function (xhr, status, error) {
                console(error);
            }

        });


    } else {

        pwResult.innerHTML = '유효하지 않은 비밀번호입니다.';

    }
}


function checkPortfolioExistence(){

    // 회원 번호를 가져오기
    var memberNo = $("label.member_no").text();

    // 포트폴리오 확인 요청
    $.get("/portfolio/check?memberNo=" + memberNo, function(response){
        var portfolioNo = response.portfolioNo;

        if(portfolioNo != null && portfolioNo !== ""){
            // 포트폴리오가 있으면 해당 페이지로 이동
            window.location.href="/portfolio/detail?portNo=" + portfolioNo;
        }
        else{
            // 포트폴리오가 없으면 확인 메시지를 띄우고, 예를 선택하면 등록 페이지로 이동
            if(confirm("등록된 포트폴리오가 없습니다. 포트폴리오를 등록하시겠습니까?")){
                window.location=href="/portfolio/regist";
            }

        }
    })
}

function logout(){
    window.location.href='/auth/logout'
}
