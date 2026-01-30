package com.example.demo.Models;
import java.time.*;
public class Orders {
    private String user;
    private ZonedDateTime date;
    private Orders(String username){
        this.user= username;
        date= ZonedDateTime.now();
    }
}
