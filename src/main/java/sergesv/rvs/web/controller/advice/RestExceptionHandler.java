package sergesv.rvs.web.controller.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import java.io.IOException;

import static sergesv.rvs.util.ValidationUtil.getConstraintViolationsMessage;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(TransactionSystemException.class)
    public void handleTransactionalException(HttpServletResponse response,
                                             TransactionSystemException exception)
            throws Throwable {
        Throwable cause = exception.getCause();

        if (!(cause instanceof RollbackException)) throw cause;
        if (!(cause.getCause() instanceof ConstraintViolationException)) throw cause.getCause();

        ConstraintViolationException violationException =
                (ConstraintViolationException) cause.getCause();
        response.sendError(HttpStatus.BAD_REQUEST.value(),
                getConstraintViolationsMessage(violationException));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleViolationException(HttpServletResponse response,
                                         ConstraintViolationException exception)
            throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),
                getConstraintViolationsMessage(exception));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(HttpServletResponse response,
                                              EntityNotFoundException exception)
            throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(HttpServletResponse response,
                                               IllegalArgumentException exception)
            throws IOException{
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
}
