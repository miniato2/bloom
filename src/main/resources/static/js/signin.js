let verifyEmail = false;
let verifyPW1 = false;
let verifyPW2 = false;
let verifyNickname = false;
let verifyName = false;
let verifyPhone = false;
let verifyage=false;
let verifyservice = false;
let verifyprivate=false;



function emailCheck(email_address){
    email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
    if(!email_regex.test(email_address)){
        return false;
    }else{
        return true;
    }
}
function validateEmail() {
    let emailInput = document.getElementById('email');


    let email = emailInput.value;

    if (emailCheck(email)) {

        alert('유효한 이메일 주소입니다.')

    } else {

        alert('유효하지 않은 이메일 주소입니다.')
    }
}


function duplEmail() {

    const email = $("#email").val();

    console.log(email);

    if (emailCheck(email)) {

        alert('유효한 이메일 주소입니다.');


        $.ajax({
            url: "/member/duplicationEmail",
            type: 'POST',
            data: {
                email: email
            },
            success: function (data, status, xhr) {
                console.log(data);
                if (data.status === "success") {
                    alert(data.message);
                    dataSend();

                } else {
                    alert(data.message);
                    return;
                }

            },
            error: function (xhr, status, error) {
                console(error);
            }

        });
    } else{
        alert('유효하지 않은 이메일 주소입니다.')
    }

}



function dataSend(){
    const email =$("#email").val();

        $.ajax({
            url: "/member/email",
            type: 'POST',
            data : {
                email : email,
            },
            success:function(data,status,xhr){
                alert("인증메일 발송 완료")

            },
            error: function(xhr,status,error) {
                console(error);
            }
        });

}

function codeSend(){
    const code =$("#authCode").val();

    $.ajax({
        url: "/member/verify",
        type: 'POST',
        data : {
           code : code,
        },
        success:function(data,status,xhr){
           if(data.status==="success"){

               alert(data.message);
               verifyEmail=true;
               console.log(verifyEmail);

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

function phoneCheck(phone){
    phone_regex1 = /^010[0-9]{8}$/;


    if(phone_regex1.test(phone)){
        return true;
    }else{
        return false;
    }
}
function validatePhone() {
    let phoneInput = document.getElementById('phone');
    let phoneResult = document.getElementById('phoneResult');

    let phone = phoneInput.value;

    if (phoneCheck(phone)) {
        phoneResult.innerHTML='사용가능 전화번호 입니다.';
        verifyPhone=true;
        console.log(verifyPhone);

    } else {
        verifyPhone=false;
        phoneResult.innerHTML='사용불가 전화번호 입니다.';
    }
}

function nameCheck(name){
    name_regex =  /^[가-힣a-zA-Z]{2,20}$/;



    if(name_regex.test(name)){
        return true;
    }else{
        return false;
    }

}

function validateName() {
    let nameInput = document.getElementById('name');
    let nameResult=document.getElementById('nameResult');

    let name = nameInput.value;

    if (nameCheck(name)) {
verifyName=true;
       nameResult.innerHTML='사용가능 이름 입니다.';
       console.log(verifyName);

    } else {
        verifyName=false;
        nameResult.innerHTML='사용불가 이름 입니다.';
    }
}

function PWCheck(PW){
    pw_regex =  /^[0-9a-zA-Z]{12,15}$/;



    if(pw_regex.test(PW)){
        return true;
    }else{
        return false;
    }

}

function validatePW() {
    let PWInput = document.getElementById('pw');
    let pwResult = document.getElementById('pwResult')


    let PW = PWInput.value;

    if (PWCheck(PW)) {
        verifyPW1=true;
       pwResult.innerHTML = '유효한 비밀번호 입니다.';
       console.log(verifyPW1);

    } else {
        verifyPW1=false;
        pwResult.innerHTML = '유효하지 않은 비밀번호입니다.';

    }
}


function validatePW2() {
    let PWInput = document.getElementById('pw');
    let PWInput2 = document.getElementById('pw2');
    let pw2Result=document.getElementById('pw2Result');


    let PW = PWInput.value;
    let PW2 = PWInput2.value;

    if (PW===PW2) {

        pw2Result.innerHTML='두 비밀번호가 일치합니다.';
        verifyPW2=true;
        console.log(verifyPW2);

    } else {

        pw2Result.innerHTML='두 비밀번호가 일치하지않습니다.';
        verifyPW2=false;
    }
}

function nicknameCheck(nickname){
    nickname_regex =  /^[가-힣a-zA-Z0-9]{3,20}$/;



    if(nickname_regex.test(nickname)){
        return true;
    }else{
        return false;
    }

}

function validateNickname() {
    let nicknameInput = document.getElementById('nickname');
    let nickname = nicknameInput.value;
    let nicknameResult = document.getElementById('nicknameResult');


    if (nicknameCheck(nickname)) {

        nicknameResult.innerHTML='사용가능 닉네임 입니다.';
        verifyNickname=true;
        console.log(verifyNickname);

    } else {

        nicknameResult.innerHTML='사용불가 닉네임 입니다.';
        verifyNickname=false;
    }
}


function age() {
    if(verifyage===true){
        verifyage=false;
        console.log(verifyage)
    }else{
        verifyage=true;
        console.log(verifyage)
    }
}
function service() {
    if(verifyservice===true){
        verifyservice=false;
        console.log(verifyservice)
    }else{
        verifyservice=true;
        console.log(verifyservice)
    }
}
function privateInfo() {
    if(verifyprivate===true){
        verifyprivate=false;
        console.log(verifyprivate)
    }else{
        verifyprivate=true;
        console.log(verifyprivate)
    }
}



    setInterval(checkAll, 1000); // 1초에 한 번씩 checkAll 함수를 실행



function checkAll(){



    if(verifyEmail && verifyPW1 && verifyPW2 && verifyNickname && verifyName && verifyPhone && verifyage && verifyservice && verifyprivate){

        $("#signinButton").prop('disabled',false);

    }else {
        $("#signinButton").prop('disabled', true);
    }


}