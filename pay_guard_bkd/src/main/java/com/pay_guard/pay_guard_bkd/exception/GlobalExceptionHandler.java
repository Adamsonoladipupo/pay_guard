package com.pay_guard.pay_guard_bkd.exception;

import com.pay_guard.pay_guard_bkd.dtos.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Business Exception",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
