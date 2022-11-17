package com.forwiz.pms.domain.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//validator of enum type 
public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> { // 정의된 어노테이션인 ValidEnum 을 Enum 이용
	private ValidEnum annotation;

	@Override
	public void initialize(ValidEnum constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	// 실제 Validation에 사용할 코드는 isValid 를 통해 구현됨
//    Enum 의 경우 직접 == 비교가 가능
	@Override
	public boolean isValid(Enum value, ConstraintValidatorContext context) {
		boolean result = false;
		Object[] enumValues = this.annotation.enumClass().getEnumConstants();
		if (enumValues != null) {
			for (Object enumValue : enumValues) {
				if (value == enumValue) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
}