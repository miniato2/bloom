function ToggleImg(event){
    const bookmarkimg = event.target;
    console.log(bookmarkimg.src)
    const id = bookmarkimg.id;

    if(id.startsWith('bookmarkImg')){

        console.log('bookmarkimgsrc : ' + bookmarkimg.src);
        console.log('id: ' + id);

        if(bookmarkimg.src =='http://localhost:8080/img/bookmark_on.png')
        {
            bookmarkimg.src = 'http://localhost:8080/img/bookmark_off.png';
        }
        else if(bookmarkimg.src == 'http://localhost:8080/img/bookmark_off.png'){
            bookmarkimg.src = 'http://localhost:8080/img/bookmark_on.png';
        }
    }

}



/*function navigateToPage(pageNumber){
    var buttons = document.getElementsByClassName("portfolio_paging_num");

    for(var i = 0; i < buttons.length; i++){
        buttons[i].style.backgroundColor = 'white';
    }


    clickedButton.style.backgroundColor = "#25432A";


    // AJAX를 사용하여 페이지 내용을 비동기식으로 로드
    var xhr = new XMLHttpRequest();
    xhr.onreadystateChange = function(){
        if(xhr.readyState === XMLHttpRequest.DONE){
            if(xhr.status === 200){
                document.getElementsByClassName("portfolio_product").innerHTML = xhr.responseText;
            }else{
                console.error("Failed to load page content");
            }
        }
    };

    xhr.open("GET", "/main?page=" + pageNumber, true);  //페이지 로드 URL
    xhr.send();
}*/


