package com.inspired.annotation.example;

import java.time.LocalDate;
import javax.validation.constraints.*;

@DateOrder(dateFieldsAscending = {"effectiveDate", "expirationDate"},
			message = "Expiration date must be after the effective date")
public class UserInputForm {

	@Past(message = "Just to make a point")
	@Past(message = "Birth date must be in the past")
	private LocalDate birthDate;
	
	@Future.List({
			@Future(message = "The earliest effective date can be tomorrow"),
			@Future(message = "Same as above")
	})
	private LocalDate effectiveDate;
	
	private LocalDate expirationDate;

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
