document.addEventListener('DOMContentLoaded', function() {
    // 체크박스와 버튼을 변수에 할당합니다.
    var checkBox = document.getElementById('serviceAgreement');
    var button = document.querySelector('.findButton');


    checkBox.addEventListener('change', function() {
        // 체크박스가 체크되면 버튼을 활성화, 아니라면 비활성화합니다.
        if(this.checked) {
            button.disabled = false;
        } else {
            button.disabled = true;
        }
    });
});