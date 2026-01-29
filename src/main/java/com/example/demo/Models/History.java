package com.example.demo.Models;
import java.time.*;
public class History {
    private String user;
    private ZonedDateTime date;
    private History(String username){
        this.user= username;
        date= ZonedDateTime.now();
    }
}
