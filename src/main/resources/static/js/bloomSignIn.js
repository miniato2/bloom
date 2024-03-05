function emailCheck(email_address){     
    email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
    if(!email_regex.test(email_address)){ 
      return false; 
    }else{
      return true;
    }
  }
  function validateEmail() {
    let emailInput = document.getElementById('email');
    
  
    let email = emailInput.value;
  
    if (emailCheck(email)) {
      
      alert('유효한 이메일 주소입니다.')
    } else {
      
      alert('유효하지 않은 이메일 주소입니다.')
    }
  }


function phoneCheck(phone){     
    phone_regex1 = /^010[0-9]{8}$/;
   
   
    if(phone_regex1.test(phone)){ 
      return true; 
    }else{
      return false;
    }
  }
  function validatePhone() {
    let phoneInput = document.getElementById('phone');
    
  
    let phone = phoneInput.value;
  
    if (phoneCheck(phone)) {
      
      alert('유효한 전화번호 입니다.')
    } else {
     
      alert('유효하지 않은 전화번호 입니다.')
    }
  }



  