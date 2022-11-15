package com.forwiz.pms.domain.annotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FileSizeValidator implements ConstraintValidator<FileSize, List<MultipartFile>> {

    private Integer min;
    private Integer max;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<MultipartFile> value, ConstraintValidatorContext context) {
        return value.size()>=min && value.size()<=max;
    }

}
