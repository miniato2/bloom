$(document).ready(function (){
    //페이지 로드 되자마자 함수 실행 여기서 판매자 구매자 신청서상태등에따라 버튼 보일지 말지 구현 //ㅇㅇㅇ
    let status = document.getElementById("requestStatus").value;

    const request = document.querySelector('.request');
    const decide = document.querySelector('.decide');
    const cancel = document.querySelector('.cancel');

    console.log(status);

    switch(status){
        case 'w' :
            request.style.display = "none";
            decide.style.display = "none";
            cancel.style.display = "block";
            break; //대기중
        case 'p' :
            request.style.display = "none";
            decide.style.display = "none";
            cancel.style.display = "none";
            break; //작업중
        case 'c' :
            request.style.display = "none";
            decide.style.display = "none";
            cancel.style.display = "none";
            break;  //취소
        case 'd' :
            //가이드 수정 횟수가 남았으면
            request.style.display = "block";
            decide.style.display = "block";
            cancel.style.display = "none";
            break; //완료
    }



})




