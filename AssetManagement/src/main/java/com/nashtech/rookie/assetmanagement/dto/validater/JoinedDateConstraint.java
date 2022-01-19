package com.nashtech.rookie.assetmanagement.dto.validater;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint(validatedBy = JoinedDateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinedDateConstraint {
	String message() default "Joined date must not in Saturday or Sunday";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
