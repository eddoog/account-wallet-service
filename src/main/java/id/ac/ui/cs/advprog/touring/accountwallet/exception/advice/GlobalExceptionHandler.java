package id.ac.ui.cs.advprog.touring.accountwallet.exception.advice;

import id.ac.ui.cs.advprog.touring.accountwallet.exception.*;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.*;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.forgotpassword.InvalidOTPCodeException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.forgotpassword.WrongOTPCodeException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.InvalidTokenException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserAlreadyLoggedInException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.WrongPasswordException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.register.*;
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
            WrongPasswordException.class,
            AgeRestrictionException.class,
            InvalidBirthDateFormatException.class,
            InvalidFullNameFormatException.class,
            InvalidPhoneNumFormatException.class,
            InvalidUsernameFormatException.class,
            UsernameAlreadyUsedException.class,
            UsernameEmptyInputException.class,
            InvalidOTPCodeException.class,
            WrongOTPCodeException.class,
            PasswordLimitException.class,
            TrimmedException.class,
            InvalidEmailException.class
    })
    public ResponseEntity<Object> definedException(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        var baseException = new ErrorTemplate(
                badRequest.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(baseException, badRequest);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> generalError(Exception exception) {
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        var baseException = new ErrorTemplate(
                badRequest.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(baseException, badRequest);
    }


}


