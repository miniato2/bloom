$(document).ready(function (){
    //페이지 로드 되자마자 함수 실행 여기서 판매자 구매자 신청서상태등에따라 버튼 보일지 말지 구현
    //판매자면 사진 업로드가능
    let member = document.getElementById("memberNo").value; //회원번호
    let portNo = document.getElementById("portNo").value;   //포폴번호
    let status = document.getElementById("requestStatus").value;  //주문상태

    if(member+"_p" === portNo){
        //판매자 경우
        document.querySelector(".bott").style.display="none";
        const accept = document.querySelector('.accept');
        const reject = document.querySelector('.reject');
        const send = document.querySelector('.send');
        const list = document.getElementById('golist2');

        switch (status){
            case 'W' :
                accept.style.display = "block";
                reject.style.display = "block";
                send.style.display = "none";
                list.style.display = 'none';
                break; //대기
            case 'P' :
                accept.style.display = "none";
                reject.style.display = "none";
                send.style.display = "block";
                list.style.display = "block";
                break; //작업
            case 'C' :
                accept.style.display = "none";
                reject.style.display = "none";
                send.style.display = "none";
                list.style.display = "block";
                break; //취소
            case 'D' :
                accept.style.display = "none";
                reject.style.display = "none";
                send.style.display = "none";
                list.style.display = "block";
                break; //완료
        }

    }else{
        //의뢰인경우
        document.querySelector(".bott2").style.display="none";

        const request = document.querySelector('.request');
        const decide = document.querySelector('.decide');
        const cancel = document.querySelector('.cancel');

        const orderFinal = document.getElementById('orderFinal').value;


        switch(status){
            case 'W' :
                request.style.display = "none";
                decide.style.display = "none";
                cancel.style.display = "block";
                break; //대기중
            case 'P' :
                request.style.display = "none";
                decide.style.display = "none";
                cancel.style.display = "none";
                break; //작업중
            case 'C' :
                request.style.display = "none";
                decide.style.display = "none";
                cancel.style.display = "none";
                break;  //취소
            case 'D' :
                cancel.style.display = "none";
                if(orderFinal == "Y"){
                    request.style.display = "none";
                    decide.style.display = "none";
                }else{
                    request.style.display = "block";
                    decide.style.display = "block";
                }
                break; //완료
        }
    }
})
//가이드 사진 표시


//사진 업로드 미리보기
function viewImg(e){
    let guidepic = document.getElementById('gpicUpload');

    guidepic.removeChild(guidepic.firstChild);

    let reader = new FileReader();

    reader.onload = (e) => {
        let img = document.createElement("img");
        img.setAttribute("src", e.target.result);
        img.style.width = "100%";
        img.style.height = "100%";
        guidepic.appendChild(img);
    };
    reader.readAsDataURL(e.target.files[0]);
}

//의뢰인 수정 요청
function requestFix(){

    //수정횟수가 다 차면 수정요청 막는 로직

    $.ajax({
        url: "/mypage/updateStatus",
        method: "POST",
        data: {
            orderNo: document.getElementById('orderNo').value,
            reqStatus: 'P'
        },
        success: function (data){
            location.reload();
            console.log(data);
        },
        error: function (xhr, status, error){
            console.log(error);
        }
    })
}

//의뢰인 구매 확정
function purchaseConfirm(){
    $.ajax({
        url: "/mypage/purchaseConfirm",
        method: "POST",
        data: {
            orderNo: document.getElementById('orderNo').value,
            reqStatus: 'D'
        },
        success: function (data){
            location.reload();
            console.log(data);
        },
        error: function (xhr, status, error){
            console.log(error);
        }
    })
}

//의뢰인 결제 취소
function cancelPay(){

}


//판매자 수락
function acceptOrder(){
    $.ajax({
        url: "/mypage/updateStatus",
        method: "POST",
        data: {
            orderNo: document.getElementById('orderNo').value,
            reqStatus: 'P'
        },
        success: function (data){
            location.reload();
            console.log(data);
        },
        error: function (xhr, status, error){
            console.log(error);
        }
    })
}


//판매자 거절
function rejectOrder(){
    $.ajax({
        url: "/mypage/updateStatus",
        method: "POST",
        data: {
            orderNo: document.getElementById('orderNo').value,
            reqStatus: 'C'
        },
        success: function (data){
            location.reload();
            console.log(data);
        },
        error: function (xhr, status, error){
            console.log(error);
        }
    })
}

//뒤로 (목록으로)
function goBack() {
    window.history.back();
    return true;
}





