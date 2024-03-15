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

