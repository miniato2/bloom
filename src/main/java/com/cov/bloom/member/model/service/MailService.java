package com.cov.bloom.member.model.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {




    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendMail(String email, String authCode) {


        SimpleMailMessage simpleMessage = new SimpleMailMessage();


        

        simpleMessage.setTo(email);


        simpleMessage.setSubject("블룸 인증번호 입니다.");


        simpleMessage.setText("인증번호는 " + authCode + " 입니다.");


        javaMailSender.send(simpleMessage);
    }

    private String createAuthCode() {

        return String.valueOf((int)(Math.random() * 1000000));
    }
}