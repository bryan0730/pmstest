package com.forwiz.pms.web.exception;

import javax.servlet.http.HttpServletRequest;

import com.forwiz.pms.domain.organization.exception.DeleteListEmptyException;
import com.forwiz.pms.domain.rank.exception.NotSaveRankException;
import com.forwiz.pms.domain.user.exception.IdDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.forwiz.pms.domain.board.exception.AccessDenied;
import com.forwiz.pms.domain.file.exception.NoSearchFileException;
import com.forwiz.pms.domain.message.exception.MessageException;
import com.forwiz.pms.domain.message.exception.NoSearchMessageException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NoSearchMessageException.class)
    public String messageSearchExceptionHandler(NoSearchMessageException e){
        log.info("ExceptionHandler NoSearchMessageException : {}", e.toString());

        return "redirect:/pms/message/receive";
    }
    
    /**
     [board] 파일 다운로드 시 파일을 찾을 수 없을 때
     */
    @ExceptionHandler(NoSearchFileException.class)
    public String fileSearchExceptionHandler(NoSearchFileException e, Model model) {
        log.info("ExceptionHandler NoSearchFileException : {}", e.toString());
		model.addAttribute("message",e.getMessage());
        return "error/alert_and_back";
    }

    /**
     [board] 작성자가 아닌 사용자가 수정 시 예외처리
     */
    @ExceptionHandler(AccessDenied.class)
    public String accessDeniedHandler(AccessDenied e, Model model) {
    	log.info("ExceptionHandler AccessDenied : {}", e.toString());
    	model.addAttribute("message",e.getMessage());
    	return "error/alert_and_back";
    }

    @ExceptionHandler(DeleteListEmptyException.class)
    public ResponseEntity<ErrorResponse> deleteListEmptyExceptionHandler(DeleteListEmptyException e){

        log.info("ExceptionHandler AccessDenied : {}", e.toString());
        final ErrorResponse errorResponse = new ErrorResponse("ERROR-DEL", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotSaveRankException.class)
    public String noSaveRankExceptionHandler(NotSaveRankException e, RedirectAttributes redirectAttributes){

        log.info("ExceptionHandler NotSaveRankException : {}", e.getMessage());
        redirectAttributes.addFlashAttribute("errMsg", e.getMessage());

        return "redirect:/admin/rank/"+e.getOrgId();
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorResponse> messageSelfSendExceptionHandler(MessageException e){

        final ErrorResponse errorResponse =
                new ErrorResponse("ERROR-MSG", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdDuplicatedException.class)
    public String idDuplicatedExceptionHandler(IdDuplicatedException e, RedirectAttributes redirectAttributes){
        log.error("ExceptionHandler user save IdDuplicatedException : {}", e.getMessage());
        redirectAttributes.addFlashAttribute("errmsg", e.getMessage());

        return "redirect:/admin/user";
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
