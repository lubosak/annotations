package com.inspired.annotation.example;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateOrderValidatorTest {
	
	@Test
	public void testDateOrder_threeDatesInOrder() {
		TestDateOrderThreeDatesPojo pojo = new TestDateOrderThreeDatesPojo();
		DateOrder constraint = pojo.getClass().getAnnotation(DateOrder.class);
		DateOrderValidator validator = new DateOrderValidator();
		validator.initialize(constraint);
		
		pojo.setFirst(LocalDate.MIN);
		pojo.setMiddle(LocalDate.now());
		pojo.setLast(LocalDate.MAX);
		
		Assert.assertTrue(validator.isValid(pojo, null));
	}

	@Test
	public void testDateOrder_twoDatesWrongOrder() {
		TestDateOrderThreeDatesPojo pojo = new TestDateOrderThreeDatesPojo();
		DateOrder constraint = pojo.getClass().getAnnotation(DateOrder.class);
		DateOrderValidator validator = new DateOrderValidator();
		validator.initialize(constraint);	
		
		pojo.setFirst(LocalDate.MAX);
		pojo.setMiddle(LocalDate.MIN);
		
		Assert.assertFalse(validator.isValid(pojo, null));
	}
	
	@Test
	public void testDateOrder_nullDateInBetween() {
		TestDateOrderThreeDatesPojo pojo = new TestDateOrderThreeDatesPojo();
		DateOrder constraint = pojo.getClass().getAnnotation(DateOrder.class);
		DateOrderValidator validator = new DateOrderValidator();
		validator.initialize(constraint);
		
		pojo.setFirst(LocalDate.MIN);
		pojo.setMiddle(null);
		pojo.setLast(LocalDate.MAX);
		
		Assert.assertTrue(validator.isValid(pojo, null));
	}
}
