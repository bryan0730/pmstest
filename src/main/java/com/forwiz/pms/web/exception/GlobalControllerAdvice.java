package com.forwiz.pms.web.exception;

import com.forwiz.pms.domain.message.exception.MessageException;
import com.forwiz.pms.domain.message.exception.NoSearchMessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NoSearchMessageException.class)
    public String messageSearchExceptionHandler(NoSearchMessageException e){
        log.info("ExceptionHandler NoSearchMessageException : {}", e.toString());

        return "redirect:/pms/board";
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorResponse> messageSelfSendExceptionHandler(MessageException e){

        final ErrorResponse errorResponse =
                new ErrorResponse("ERROR-MSG", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> methodValidException(BindException e, HttpServletRequest request){
        final ErrorResponse errorResponse = bindingErrorResponse(e.getBindingResult());

        log.info("request URI : {}", request.getRequestURI());
        request.setAttribute("errorResponse", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse bindingErrorResponse(BindingResult bindingResult){

        if (bindingResult.hasErrors()){

            String code = bindingResult.getFieldError().getCode();
            String message = bindingResult.getFieldError().getDefaultMessage();

            log.info("BindingError Field : {}", bindingResult.getFieldError().getField());
            log.info("BindingError Code : {}", bindingResult.getFieldError().getCode());
            log.info("BindingError message : {}", bindingResult.getFieldError().getDefaultMessage());

            return new ErrorResponse(code, message);
        }
        return new ErrorResponse();
    }
}
