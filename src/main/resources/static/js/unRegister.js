let agreement=false;

function agree() {
    if(agreement===true){
        agreement=false;
        console.log(agreement)

    }else{
        agreement=true;
        console.log(agreement)
    }
}

setInterval(checkAll, 300); // 1초에 한 번씩 checkAll 함수를 실행



function checkAll(){



    if(agreement===true){

        $("#delete").prop('disabled',false);

    }else {
        $("#delete").prop('disabled', true);
    }


}