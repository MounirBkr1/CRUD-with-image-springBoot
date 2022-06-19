package com.mnr.cours_management_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class RessourceNotFoundException  extends RuntimeException{

    public RessourceNotFoundException(String message){
        super(message);
    }
}
