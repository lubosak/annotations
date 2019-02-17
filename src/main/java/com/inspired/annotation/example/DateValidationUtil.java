package com.inspired.annotation.example;

import java.time.LocalDate;
import java.util.List;

public class DateValidationUtil {
		
	public static void dateInPast(LocalDate date, String field, String message, List<ValidationError> errors) {
		LocalDate today = LocalDate.now();
		if (!date.isBefore(today)) {
			errors.add(new ValidationError(field, message));
		}
	}

	public static void dateInFuture(LocalDate date, String field, String message, List<ValidationError> errors) {
		LocalDate today = LocalDate.now();
		if (!date.isAfter(today)) {
			errors.add(new ValidationError(field, message));
		}
	}
	
	public static void datesInOrder(LocalDate earlier, LocalDate later,
									String field, String message, List<ValidationError> errors) {
		if (!earlier.isBefore(later)) {
			errors.add(new ValidationError(field, message));
		}
	}
}
