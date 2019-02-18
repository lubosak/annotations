package com.inspired.annotation.example;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateOrderValidator implements ConstraintValidator<DateOrder, Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateOrderValidator.class);

	String[] dateFieldsAscending;
	
	@Override
	public void initialize(DateOrder constraint) {
		this.dateFieldsAscending = constraint.dateFieldsAscending();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		List<LocalDate> dates = new ArrayList<>();
		for (String field : dateFieldsAscending) {
			try {
				PropertyDescriptor fieldDescriptor = new PropertyDescriptor(field, value.getClass());
				LocalDate date = (LocalDate) fieldDescriptor.getReadMethod().invoke(value);
				dates.add(date);
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.warn("Could not access field " + field + " on class " + value.getClass().getName());
			}
		}
		LocalDate previousDate = null;
		for (LocalDate date : dates) {
			if (date == null) {
				continue;
			}
			if (previousDate != null && !previousDate.isBefore(date)) {
				return false;
			}
			previousDate = date;
		}
		return true;
	}

}
