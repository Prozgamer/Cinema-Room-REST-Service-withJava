package cinema.Advice;

import cinema.Exceptions.BussinesException;
import cinema.Exceptions.WrongPassException;
import cinema.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@RestControllerAdvice
public class WrongPassExceptionHandler {
    @ExceptionHandler(WrongPassException.class)
    public ErrorResponse handleException(WrongPassException e) {
        return new ErrorResponse(e.getMessage());
    }
}
