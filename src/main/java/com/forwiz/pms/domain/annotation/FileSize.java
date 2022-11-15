package com.forwiz.pms.domain.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.ElementType.FIELD;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import java.lang.annotation.Target;


@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FileSizeValidator.class)
@Documented
public @interface FileSize {

    //유효하지 않을 경우 반환할 메시지
    String message() default "등록할 수 있는 파일의 개수를 확인해주세요.";

    //유효성 검증이 진행될 그룹
    Class<?>[] groups() default {};

    //유효성 검증시에 전달할 메타 정보
    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default Integer.MAX_VALUE;

}
