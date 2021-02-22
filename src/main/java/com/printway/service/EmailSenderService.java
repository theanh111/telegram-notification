package com.printway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sentInsufficientFundsMail(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("tuanlv@printway.io");
        msg.setSubject("Thẻ Hết Tiền");
        msg.setText("Mời bạn vào thanh toán lại cho mình");
        javaMailSender.send(msg);
    }

    public void sentChangePaymentMethod(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("tuanlv@printway.io");
        msg.setSubject("Thẻ Không hỗ trợ tự động thanh toán, cần xác thực người dùng");
        msg.setText("Mời bạn vào thêm 1 phương thúc thanh toán khác");
        javaMailSender.send(msg);
    }
}
