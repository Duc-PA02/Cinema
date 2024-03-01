package com.example.cinema.exceptions;

public class ConfirmEmailExpired extends Exception{
    public ConfirmEmailExpired(String msg){
        super(msg);
    }
}
