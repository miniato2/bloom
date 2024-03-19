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


 