package misrraimsp.theam.crm.controller;


import misrraimsp.theam.crm.util.BadImageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
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
        LOGGER.debug(ex.getMessage());
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
        LOGGER.debug(ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                this.buildErrorMessage(ex.getMessage()),
                httpHeaders,
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseBody
    @ExceptionHandler(BadImageException.class)
    public ResponseEntity<?> badImageExceptionHandler(BadImageException ex) {
        LOGGER.debug(ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                this.buildErrorMessage(ex.getMessage()),
                httpHeaders,
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseBody
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<?> transactionSystemExceptionHandler(TransactionSystemException ex) {
        LOGGER.debug(ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                buildErrorMessage(ex.getMostSpecificCause().getMessage()),
                httpHeaders,
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseBody
    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> IOExceptionHandler(IOException ex) {
        LOGGER.debug(ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                this.buildErrorMessage(ex.getMessage()),
                httpHeaders,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private String buildErrorMessage(String text) {
        return "{\"errorMessage\": " +
                "\"" +
                text
                    .replaceAll("\\s", " ")
                    .replaceAll("\"", "'") +
                "\"}";
    }
}
