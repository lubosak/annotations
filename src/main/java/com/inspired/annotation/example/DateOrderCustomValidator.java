package com.inspired.annotation.example;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.inspired.annotation.finder.ITypeAnnotationFinder;
import com.inspired.annotation.finder.spring.TypeAnnotationFinder;

import lombok.NonNull;

public class DateOrderCustomValidator {

	private ITypeAnnotationFinder<DateOrder> dateOrderFinder = new TypeAnnotationFinder<>(DateOrder.class);
	
	public List<ValidationError> validate(@NonNull Object pojo) {
		List<ValidationError> errors = new ArrayList<>();
		
		List<DateOrder> constraints = dateOrderFinder.onType(pojo.getClass());
		if (constraints == null || constraints.isEmpty()) {
			return errors;
		}
		
		for (DateOrder constraint : constraints) {
			if (!isValid(pojo, constraint)) {
				errors.addAll(generateErrors(constraint));
			}
		}

		return errors;
	}

	private boolean isValid(@NonNull Object pojo, @NonNull DateOrder constraint) {
		List<LocalDate> dates = new ArrayList<>();
		for (String field : constraint.dateFieldsAscending()) {
			try {
				PropertyDescriptor fieldDescriptor = new PropertyDescriptor(field, pojo.getClass());
				LocalDate date = (LocalDate) fieldDescriptor.getReadMethod().invoke(pojo);
				dates.add(date);
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				//LOGGER.warn("Could not access field " + field + " on class " + pojo.getClass().getName());
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

	private List<ValidationError> generateErrors(@NonNull DateOrder constraint) {
		List<ValidationError> errors = new ArrayList<>();
		for (String fieldName: constraint.dateFieldsAscending()) {
			errors.add(new ValidationError(fieldName, constraint.message()));
		}
		return errors;
	}

}
