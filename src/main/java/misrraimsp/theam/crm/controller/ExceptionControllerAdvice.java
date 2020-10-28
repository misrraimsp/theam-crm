package misrraimsp.theam.crm.controller;


import misrraimsp.theam.crm.util.EntityNotFoundByIdException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundByIdException.class)
    public ResponseEntity<?> entityNotFoundHandler(EntityNotFoundByIdException ex) {
        Map<String,String> map = new HashMap<>();
        map.put("errorMessage", ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(map,httpHeaders,HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> httpClientErrorHandler(HttpClientErrorException ex) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getResponseBodyAsString(),httpHeaders,ex.getStatusCode());
    }

}
