function previewFile() {
    const fileInput = document.getElementById('photo_select');

    
    const reader = new FileReader();
    const previewPhoto = document.querySelectorAll('.selected_photobox_img');

    let count = 0;
    
    reader.readAsDataURL(fileInput.files[0]);
    reader.onload = function(e) {

        if(document.querySelectorAll('.selected_photobox_img.active').length != previewPhoto.length){
            
            previewPhoto.forEach((item, index) => {

                let isActive = item.getAttribute('class').indexOf('active')
                // active 클래스가 있는지 확인
    
                if(isActive == -1) {
                    //active 클래스가 없으면
    
                    if(count == 0) {
    
                        item.classList.add('active')
                        item.querySelector('img').src = e.target.result
                        count += 1
    
                    }
                    else {
                        return
                    }
                }
            })
        }else{
            alert ('이미지는 5개까지 입력가능합니다.');
        }

    }
}

function photoDelete(){
    const previewPhoto = document.querySelectorAll('.selected_photobox_img');
    
    previewPhoto.forEach(item => {
        item.querySelector('img').src = 'img/selected_photo.png';
        item.classList.remove('active');
    })

}