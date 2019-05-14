package com.mhfelippi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private static Logger LOGGER = Logger.getLogger(ApplicationExceptionHandler.class.getName());

    @ExceptionHandler(DiffFileNotFoundException.class)
    public ResponseEntity<DiffError> handleError(DiffFileNotFoundException exception) {
        DiffFile file = exception.getFile();

        String error = null;
        String message = null;

        switch (exception.getFile().getSide()) {
            case LEFT:
                error = "LEFT_FILE_NOT_FOUND";
                message = "Left file with id " + file.getId() + " not found.";
            case RIGHT:
                error = "RIGHT_FILE_NOT_FOUND";
                message = "Right file with id " + file.getId() + " not found.";
        }

        return new ResponseEntity<>(new DiffError(error, message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnhandledException.class)
    public ResponseEntity handleError(UnhandledException exception) {
        LOGGER.log(Level.WARNING, "Unhandled exception.", exception);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
