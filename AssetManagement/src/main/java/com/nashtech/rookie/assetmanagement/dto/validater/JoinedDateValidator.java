package com.nashtech.rookie.assetmanagement.dto.validater;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nashtech.rookie.assetmanagement.utils.DateUtils;

public class JoinedDateValidator implements ConstraintValidator<JoinedDateConstraint, LocalDate> {

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return !DateUtils.isWeekend(value);
	}



}
