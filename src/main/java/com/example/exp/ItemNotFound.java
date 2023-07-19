package com.example.exp;

public class ItemNotFound extends RuntimeException{
    public ItemNotFound(String message) {
        super(message);
    }
}
