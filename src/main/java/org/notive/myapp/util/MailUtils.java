package org.notive.myapp.util;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtils {
    private JavaMailSender mailSender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    public MailUtils(JavaMailSender mailSender) throws MessagingException {
        this.mailSender = mailSender;
        message = this.mailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    } //constructor

    
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
        
    } //setSubject
    
    
    public void setText(String htmlContent) throws MessagingException {
        messageHelper.setText(htmlContent, true);
        
        //  이메일 TEXT 부분 
    } //setText
    
    
    public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
        messageHelper.setFrom(email, name);
        // 보내는 사람 이메일 
    }//setFrom
    
    
    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
        //받는 사람 이메일 
    } //setTo
    
    
    //--------------------------- 캐스팅 왜 필요?????????????????????????? --------------------//
    public void addInline(String contentId, DataSource dataSource) throws MessagingException {
        messageHelper.addInline(contentId, (javax.activation.DataSource) dataSource);
    } //addInline
    
    
    public void send() {
        try {
            mailSender.send(message);
        }catch (Exception e) {
            e.printStackTrace();
        } //try-catch
    } //send
	
} //end class
