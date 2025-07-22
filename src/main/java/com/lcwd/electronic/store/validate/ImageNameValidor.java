package com.lcwd.electronic.store.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNameValidor implements ConstraintValidator<ImageNameValid, String> {

    private Logger logger = LoggerFactory.getLogger(ImageNameValidor.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Add your validation logic
        logger.info("Message From isValid : {} ", value);


        if (value == null || value.isBlank()) {
            return false;
        }
        return true;
    }
}
