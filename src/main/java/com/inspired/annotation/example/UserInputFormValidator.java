package com.inspired.annotation.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserInputFormValidator {

	public List<ValidationError> validate(UserInputForm input) {
		List<ValidationError> errors = new ArrayList<>();
		LocalDate today = LocalDate.now();
		
		if (!input.getBirthDate().isBefore(today)) {
			errors.add(new ValidationError("birthDate", "Birth date must be in the past"));
		}
		
		if (!input.getEffectiveDate().isAfter(today)) {
			errors.add(new ValidationError("effectiveDate", "The earliest effective date can be tomorrow"));
		}
		
		if (!input.getExpirationDate().isAfter(input.getEffectiveDate())) {
			errors.add(new ValidationError("expirationDate", "Expiration date must be after the effective date"));
		}
		return errors;
	}
}
