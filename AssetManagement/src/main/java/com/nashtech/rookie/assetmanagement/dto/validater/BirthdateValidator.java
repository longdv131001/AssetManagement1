package com.nashtech.rookie.assetmanagement.dto.validater;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BirthdateValidator implements ConstraintValidator<BirthdateConstraint, LocalDate>{
	
	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		return Period.between(value, LocalDate.now()).getYears()>=18;
	}

}
