package misrraimsp.theam.crm.controller;


import misrraimsp.theam.crm.util.BadImageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> httpClientErrorHandler(HttpClientErrorException ex) {
        LOGGER.info(ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                ex.getResponseBodyAsString(),
                httpHeaders,
                ex.getStatusCode()
        );
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        LOGGER.info(ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                "{\"errorMessage\": " + "\"" + ex.getMessage() + "\"}",
                httpHeaders,
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseBody
    @ExceptionHandler(BadImageException.class)
    public ResponseEntity<?> badImageExceptionHandler(BadImageException ex) {
        LOGGER.info(ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                "{\"errorMessage\": " + "\"" + ex.getMessage() + "\"}",
                httpHeaders,
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseBody
    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> IOExceptionHandler(IOException ex) {
        LOGGER.info(ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                "{\"errorMessage\": " + "\"" + ex.getMessage() + "\"}",
                httpHeaders,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
