package com.example.springap.exceptions;

public class ProductNotFoundException extends Exception{

    /* It creates an object of ProductNotFoundException and
       set the error message */
    public ProductNotFoundException(String message){

        super(message);
    }
}
