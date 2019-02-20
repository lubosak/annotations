package com.inspired.annotation.example;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DateOrderValidator.class)
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
public @interface DateOrder {

	String[] dateFieldsAscending();
	String message() default "Dates are not in proper order";
	
	//required for @Constraint by JSR-303
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}