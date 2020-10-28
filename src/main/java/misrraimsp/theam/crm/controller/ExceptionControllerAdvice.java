package misrraimsp.theam.crm.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> httpClientErrorHandler(HttpClientErrorException ex) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(ex.getResponseBodyAsString(),httpHeaders,ex.getStatusCode());
    }

}
