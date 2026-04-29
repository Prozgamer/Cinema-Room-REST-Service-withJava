package cinema.Advice;

import cinema.Exceptions.BussinesException;
import cinema.models.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CinemaErrorAspect {
    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<?> handleException(BussinesException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getMessage()));
    }
}
