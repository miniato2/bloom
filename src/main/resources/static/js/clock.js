
    var Target = document.getElementById("clock");
    function clock() {
        var time = new Date();
        var year = time.getFullYear();
        var month = time.getMonth();
        var date = time.getDate();
        var day = time.getDay();
      

        var hours = time.getHours();
        var minutes = time.getMinutes();
        var seconds = time.getSeconds();

        Target.innerText = 
        `${year}년 ${month + 1}월 ${date}일 ` +
        `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds}`;
    }
    
    window.onload = clock();
  