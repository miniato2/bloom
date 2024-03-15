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


 