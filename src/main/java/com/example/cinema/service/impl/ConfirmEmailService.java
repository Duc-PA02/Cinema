package com.example.cinema.service.impl;

import com.example.cinema.entity.ConfirmEmail;
import com.example.cinema.exceptions.ConfirmEmailExpired;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.ConfirmEmailRepository;
import com.example.cinema.service.iservice.IConfirmEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ConfirmEmailService implements IConfirmEmailService {
    @Autowired
    private ConfirmEmailRepository confirmEmailRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    private String generateConfirmCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }

    private void sendEmail(String to, String subject,String content){
        SimpleMailMessage msg =new SimpleMailMessage();
        msg.setFrom("ducpa2002@gmail.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);
    }

    public boolean isExpired(ConfirmEmail confirmEmail) {
        return LocalDateTime.now().isAfter(confirmEmail.getExpiredTime());
    }

    @Override
    public void sendConfirmEmail(String email) {
        ConfirmEmail confirmEmail = ConfirmEmail.builder()
                .userId(null)
                .confirmCode(generateConfirmCode())
                .requiredTime(LocalDateTime.now())
                .expiredTime(LocalDateTime.now().plusSeconds(120))
                .isComfirm(false)
                .build();
        confirmEmailRepository.save(confirmEmail);

        String subject = "Cinema";
        String content = "Mã xác nhận của bạn là: " +confirmEmail.getConfirmCode();
        sendEmail(email, subject, content);
    }

    @Override
    public boolean confirmEmail(String confirmCode) throws Exception {
        ConfirmEmail confirmEmail =confirmEmailRepository.findConfirmEmailByConfirmCode(confirmCode);

        if (confirmEmail == null){
            throw new DataNotFoundException("mã xác minh không chính xác");
        }
        if (isExpired(confirmEmail)){
            throw new ConfirmEmailExpired("mã xác minh đã hết hạn");
        }
        confirmEmail.setComfirm(true);
        confirmEmailRepository.save(confirmEmail);
        return true;
    }
}
