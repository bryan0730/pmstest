package com.forwiz.pms.web.exception;

import com.forwiz.pms.domain.message.exception.MessageException;
import com.forwiz.pms.domain.message.exception.NoSearchMessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
