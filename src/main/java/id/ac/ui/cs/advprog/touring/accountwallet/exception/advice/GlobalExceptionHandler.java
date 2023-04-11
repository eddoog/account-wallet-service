package id.ac.ui.cs.advprog.touring.accountwallet.exception.advice;

import id.ac.ui.cs.advprog.touring.accountwallet.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            VerificationInvalidException.class,
            UserHasBeenVerifiedException.class,
            InvalidTokenException.class,
            UserDoesExistException.class,
            UserAlreadyLoggedInException.class,
            UserNotFoundException.class,
            WrongPasswordException.class
    })
    public ResponseEntity<Object> DefinedException(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorTemplate baseException = new ErrorTemplate(
                badRequest.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(baseException, badRequest);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> generalError(Exception exception) {
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorTemplate baseException = new ErrorTemplate(
                badRequest.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(baseException, badRequest);
    }


}


