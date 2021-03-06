package com.nashtech.rookie.assetmanagement.dto.validater;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = BirthdateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthdateConstraint {
	String message() default "User is under 18";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
