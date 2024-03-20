var bigPic = document.querySelector("#big_photo");
var smallPics = document.querySelectorAll(".small_photo");


for(var i = 0; i < smallPics.length; i++){
    smallPics[i].addEventListener("click", changepic);
}

function changepic(){
    var smallPicAttribute = this.getAttribute("src");
    bigPic.setAttribute("src", smallPicAttribute);
}

const rating_input = document.querySelector('.rating input');
const rating_star = document.querySelector('.rating_star');
const currentRating = document.getElementById('currentRating');

// 별점 드래그 할 때
rating_input.addEventListener('input', () => {
  rating_star.style.width = `${rating_input.value * 10}%`;
  const value = parseFloat(rating_input.value).toFixed(1);
  currentRating.innerText = value / 2;
});

function messageSend(){
    const nickname = $("#ReceiverNick").text().trim();
    const encodedNickname = encodeURIComponent(nickname); // 닉네임을 URL 인코딩합니다.
    const memberNo = $("#memberNo").text().trim();

    $.ajax({
        url:"/message/send/" + memberNo,
        type: 'GET',
        data:{
            nickname: encodedNickname
        },
        success: function (response){
            console.log('요청이 완료되었습니다.');
            console.log(response);
        },
        error: function (xhr, status, error){
            console.error('요청이 실패했습니다.');
            console.error(error);
        }
    });
}



function confirmDeletePortfolio(){
    console.log("deleteBtn 눌렀어")
    var confirmDelete = confirm('포트폴리오를 삭제하시겠습니까? \n삭제하면 복구할 수 없습니다.');

    //확인을 선택한 경우에만 포트폴리오 삭제 함수 호출
    if(confirmDelete){
        deletePortfolio();
    }
}

//포트폴리오 삭제 요청 및 응답
function deletePortfolio(){
    //포트폴리오 번호 가져오기
    var portNo = $("#portNo").text();

    $.ajax({
        url: "/portfolio/delete",
        type: "DELETE",
        data : { portNo : portNo },
        success : function(response){
            // 삭제 성공
            alert("포트폴리오가 삭제되었습니다. \n메인 화면으로 이동합니다.");
            window.location.href = "/main";
        },
        error: function(xhr, status, error){
            //삭제 실패
            alert("포트폴리오 삭제 중 오류가 발생했습니다. : " + error);

        }


    })
}

//들여쓰기를 적용할 함수
function applyIndentation(text){
    //각 줄에 들여쓰기 적용
    var lines = text.split('\n');
    for(var i = 0; i< lines.length; i++){
           lines[i] = "    " + lines[i];        //4개의 공백 추가
    }
     return lines.join('\n');       //줄바굼 문자로 연결하여 반환
}

//들여쓰기를 해줄 텍스트
var service_contents = document.querySelector(".service_contents").innerText;

//들여쓰기가 적용된 텍스트 생성
var textWithIndentation = applyIndentation(service_contents);

// 필요한 HTML 요소에 들여쓰기가 적용된 텍스트를 설정
document.querySelector(".service_contents").innerText = textWithIndentation;