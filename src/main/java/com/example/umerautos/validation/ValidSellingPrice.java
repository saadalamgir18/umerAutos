package com.example.umerautos.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SellingPriceValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSellingPrice {
    String message() default "Selling price must be greater than purchase price";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
