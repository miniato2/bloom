let verifyPW2 = false;
let verifyPW1 = false;

function PWCheck(pw){
    pw_regex =  /^[0-9a-zA-Z]{12,15}$/;



    if(pw_regex.test(pw)){
        return true;
    }else{
        return false;
    }

}

function validatePW1(){
    let PWInput = document.getElementById('findpw1');
    let pw1Result = document.getElementById('pwResult')
    if(PWCheck(PWInput.value)){
        pw1Result.innerHTML='사용가능한 비밀번호입니다';
        verifyPW1=true;

    }else{
        pw1Result.innerHTML='비밀번호가 유효하지 않습니다.';
    }
}

function validatePW2() {
    let PWInput = document.getElementById('findpw1');
    let PWInput2 = document.getElementById('findpw2');
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
setInterval(activePWButton, 1000); // 1초에 한 번씩 checkAll 함수를 실행

function activePWButton (){
    if((verifyPW2===true) && (verifyPW1===true) ){
        $("#findPWButton").prop('disabled',false);
    }else{
        $("#findPWButton").prop('disabled',true);
    }
}