package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(){
        return new ResponseEntity<>("User with given ID doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Object> handleMovieNotFoundException(){
        return new ResponseEntity<>("Movie with given ID doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieCollectionNotFoundException.class)
    public ResponseEntity<Object> handleMovieCollectionNotFoundException(){
        return new ResponseEntity<>("Movie collection with given ID doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<Object> handleSongNotFoundException(){
        return new ResponseEntity<>("Song with given ID doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SongCollectionNotFoundException.class)
    public ResponseEntity<Object> handleSongCollectionNotFoundException(){
        return new ResponseEntity<>("Song collection with given ID doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
