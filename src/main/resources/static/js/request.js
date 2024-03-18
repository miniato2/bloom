function checkForm(){
    let file = document.querySelector("#file1").value;
    let text = document.querySelector(".text").value;
    let optionValue = $("#option").val();
    let files = document.getElementsByClassName("file");

    if(text.trim() === '' || file === ''){
        alert('신청서를 작성해 주세요');
        return false;
    }
    if (optionValue === '선택'){
        alert('옵션을 선택해 주세요');
        return false;
    }
    for(i = 0; i < files.length; i++){
        if(files[i].value === ''){
            files[i].disabled = true;
        }
    }

    return true;
}
function goBack() {
    window.history.back();
    return true;
}

let num = 1;
function viewImg(e){

    if(num > 5){
        alert('이미지는 최대 5장까지 첨부가능합니다. ');
        return;
    }

    let label = document.getElementById('upload');
    let picId = 'pic' + num;
    let nextId = 'file' + (num+1);

    let reader = new FileReader();

    reader.onload = (e) => {
        let img = document.createElement("img");
        img.setAttribute("src", e.target.result);
        img.style.width = "100%";
        img.style.height = "100%";

        document.getElementById(picId).appendChild(img);

    };
    reader.readAsDataURL(e.target.files[0]);
    label.setAttribute("for", nextId);
    num += 1;
    //마지막엔 라벨 for 속성을 바꿔서 다음 인풋에 연결되도록 만듦

}

function deleteFile(){
    let label = document.getElementById('upload');
    let files = document.querySelectorAll('.file');
    let pics = document.querySelectorAll('.pic');

    for(i = 0; i < files.length; i++){
        if(files[i].value !== ''){
            files[i].value = '';
        }
    }
    for(i = 0; i < pics.length; i++){
        if(pics[i].firstChild !== null){
            pics[i].removeChild(pics[i].firstChild);
        }
    }
    num = 1;
    label.setAttribute("for", "file1");

}

function selectOption() {
    let optionValue = $("#option").val();
    let portNo = $("#portNo").val();

    let totalprice = document.querySelector(".totalprice2");

    let dateinput = document.getElementById("date");
    let fixinput = document.getElementById("fix");
    let priceinput = document.getElementById("price");

    let date = document.querySelector('.date2');
    let fix = document.querySelector('.fix2');
    let price = document.querySelector('.price2');

    if(optionValue === '선택'){
        dateinput.value='';
        fixinput.value='';
        priceinput.value='';

        date.innerHTML='';
        fix.innerHTML='';
        price.innerHTML='';

        totalprice.innerHTML='';
    }else {
        $.ajax({
            url: "/order/getOption",
            method: "GET",
            data: {
                optionNo : portNo + "_op" + optionValue
            },
            success: function(data) {
                let selected = JSON.parse(data.option);

                date.innerHTML=selected.optionDt;
                fix.innerHTML=selected.optionFix;
                price.innerHTML=selected.optionPrice;

                dateinput.value=selected.optionDt;
                fixinput.value=selected.optionFix;
                priceinput.value=selected.optionPrice;

                totalprice.innerHTML=selected.optionPrice+'원';
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
    }
}