package misrraimsp.theam.crm.controller;


import lombok.Getter;
import lombok.NoArgsConstructor;
import misrraimsp.theam.crm.util.EntityNotFoundByIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error entityNotFoundHandler(EntityNotFoundByIdException ex) {
        return new Error("EntityNotFoundByIdException", ex.getMessage());
    }

    @Getter
    @NoArgsConstructor
    static class Error {

        private String name;
        private String message;

        Error(String name, String message) {
            this.name = name;
            this.message = message;
        }
    }

}
