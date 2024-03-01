package com.example.cinema.service.iservice;

public interface IConfirmEmailService {
    void sendConfirmEmail(String email);
    boolean confirmEmail(String confirmCode) throws Exception;
}
