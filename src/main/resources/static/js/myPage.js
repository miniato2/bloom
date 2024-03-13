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

    // 해당 요소의 disabled 속성을 false로 설정해 활성화합니다.
    phoneInput.disabled = true;

    // 선택적으로, 사용자가 입력을 시작할 수 있도록 해당 필드에 포커스를 줍니다.


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




