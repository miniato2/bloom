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








function dataSend(){
    const email =$("#email").val();
    if (emailCheck(email)) {

        alert('유효한 이메일 주소입니다.');

        $.ajax({
            url:"/member/duplicationEmail",
            type: 'POST',
            data : {
                email : email
            },

            success:function(data,status,xhr){
                console.log(data);
                if(data.status==="success"){
                    alert(data.message);
                }
                else{
                    alert(data.message);
                    return;
                }

            },
            error: function(xhr,status,error) {
                console(error);
            }
        });


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

    else {

        alert('유효하지 않은 이메일 주소입니다.')
    }


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
               $("#signinButton").prop('disabled',false);
               alert(data.message);
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