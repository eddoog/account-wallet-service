package id.ac.ui.cs.advprog.touring.accountwallet.exception;

import id.ac.ui.cs.advprog.touring.accountwallet.exception.advice.GlobalExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class GlobalExceptionHandlerTest {

    @Test
    void testDefinedException() {
        // Setup
        var globalExceptionHandler = new GlobalExceptionHandler();
        var userDoesExistException = new UserDoesExistException("test@test.com");

        // Test
        var actualResponse = globalExceptionHandler.definedException(userDoesExistException);

        // Verify
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertEquals(400, ((ErrorTemplate) actualResponse.getBody()).statusCode());
        Assertions.assertEquals("User with email test@test.com has already been used", ((ErrorTemplate) actualResponse.getBody()).message());
    }

    @Test
    void testGeneralError() {
        // Setup
        var globalExceptionHandler = new GlobalExceptionHandler();
        var generalException = new RuntimeException("Something went wrong");

        // Test
        var actualResponse = globalExceptionHandler.generalError(generalException);

        // Verify
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertEquals(500, ((ErrorTemplate) actualResponse.getBody()).statusCode());
        Assertions.assertEquals("Something went wrong", ((ErrorTemplate) actualResponse.getBody()).message());
    }

    @Test
    void testDefinedExceptionUserHasBeenVerifiedException() {
        // Setup
        var globalExceptionHandler = new GlobalExceptionHandler();
        var userHasBeenVerifiedException = new UserHasBeenVerifiedException();

        // Test
        var actualResponse = globalExceptionHandler.definedException(userHasBeenVerifiedException);

        // Verify
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertEquals(400, ((ErrorTemplate) actualResponse.getBody()).statusCode());
        Assertions.assertEquals("Sorry, this account has already been verified", ((ErrorTemplate) actualResponse.getBody()).message());
    }

    @Test
    void testDefinedExceptionInvalidTokenException() {
        // Setup
        var globalExceptionHandler = new GlobalExceptionHandler();
        var invalidTokenException = new InvalidTokenException("00000000000000000000000000000000000000");

        // Test
        var actualResponse = globalExceptionHandler.definedException(invalidTokenException);

        // Verify
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertEquals(400, ((ErrorTemplate) actualResponse.getBody()).statusCode());
        Assertions.assertEquals("Token 000000000000000... is not valid", ((ErrorTemplate) actualResponse.getBody()).message());
    }

    @Test
    void testDefinedExceptionVerificationInvalid() {
        // Setup
        var globalExceptionHandler = new GlobalExceptionHandler();
        var verificationInvalidException = new VerificationInvalidException();

        // Test
        var actualResponse = globalExceptionHandler.definedException(verificationInvalidException);

        // Verify
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertEquals(400, ((ErrorTemplate) actualResponse.getBody()).statusCode());
        Assertions.assertEquals("The link is either wrong or expired", ((ErrorTemplate) actualResponse.getBody()).message());
    }

    @Test
    void testDefinedExceptionUserAlreadyLoggedInException() {
        // Setup
        var globalExceptionHandler = new GlobalExceptionHandler();
        var userAlreadyLoggedInException = new UserAlreadyLoggedInException("tes@tes.com");

        // Test
        var actualResponse = globalExceptionHandler.definedException(userAlreadyLoggedInException);

        // Verify
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertEquals(400, ((ErrorTemplate) actualResponse.getBody()).statusCode());
        Assertions.assertEquals("User with email tes@tes.com has already logged in", ((ErrorTemplate) actualResponse.getBody()).message());
    }

    @Test
    void testDefinedExceptionUserNotFoundException() {
        // Setup
        var globalExceptionHandler = new GlobalExceptionHandler();
        var userNotFoundException = new UserNotFoundException("tes@tes.com");

        // Test
        var actualResponse = globalExceptionHandler.definedException(userNotFoundException);

        // Verify
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertEquals(400, ((ErrorTemplate) actualResponse.getBody()).statusCode());
        Assertions.assertEquals("User with email tes@tes.com does not exist", ((ErrorTemplate) actualResponse.getBody()).message());
    }

    @Test
    void testDefinedExceptionWrongPasswordException() {
        // Setup
        var globalExceptionHandler = new GlobalExceptionHandler();
        var wrongPasswordException = new WrongPasswordException("tesPassword");

        // Test
        var actualResponse = globalExceptionHandler.definedException(wrongPasswordException);

        // Verify
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertEquals(400, ((ErrorTemplate) actualResponse.getBody()).statusCode());
        Assertions.assertEquals("Password ********ord", ((ErrorTemplate) actualResponse.getBody()).message());
    }
}

