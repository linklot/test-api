package au.com.llk.testapi.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String VALIDATION_ERROR = "validationError";
    private static final String NOTFOUND_ERROR = "NotFoundError";
    private static final String ILLEGAL_ARG_ERROR = "IllegalArgumentError";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        val details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> ValidationErrorDetails.builder()
                        .location(ex.getParameter().getContainingClass().getCanonicalName() + "." + ex.getParameter()
                                .getMethod().getName() + "()").param(ex.getParameter().getParameterName())
                        .msg(error.getDefaultMessage()).value(error.getRejectedValue().toString()).build())
                .collect(Collectors.toList());

        val errorResponse = new ErrorResponse<>(details, VALIDATION_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {

        List<ValidationErrorDetails> details = ex.getConstraintViolations().stream()
                .map(violation -> ValidationErrorDetails.builder()
                        .location(violation.getRootBeanClass().getCanonicalName())
                        .param(violation.getPropertyPath().toString())
                        .msg(violation.getMessage()).value(violation.getInvalidValue().toString()).build())
                .collect(Collectors.toList());

        ErrorResponse<ValidationErrorDetails> errorResponse = new ErrorResponse<>(details, VALIDATION_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TodoItemNotFoundException.class)
    protected ResponseEntity<?> handleTodoItemNotFoundException(TodoItemNotFoundException ex) {
        List<ItemNotFoundErrorDetails> details = Arrays
                .asList(ItemNotFoundErrorDetails.builder().message(ex.getMessage()).build());

        ErrorResponse<ItemNotFoundErrorDetails> errorResponse = new ErrorResponse<>(details, NOTFOUND_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        List<ItemNotFoundErrorDetails> details = Arrays.asList(ItemNotFoundErrorDetails.builder()
                .message("Illegal request argument. Does id only contain digits?").build());
        ErrorResponse<ItemNotFoundErrorDetails> errorResponse = new ErrorResponse<>(details, ILLEGAL_ARG_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
