package com.forwiz.pms.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EnumValidator.class) // 구체적인 validation 방법을 정의 (EnumValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER }) // 어노테이션이 적용될 수 있는 위치
@Retention(RetentionPolicy.RUNTIME) // runtime에도 어노테이션이 제공
public @interface ValidEnum {
	String message() default "Invalid value. This is not permitted.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	// 정의한 Enum class를 파라미터로 넘김
	Class<? extends java.lang.Enum<?>> enumClass();
}