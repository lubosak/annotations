package com.inspired.annotation.finder;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;

public abstract class AbstractAnnotationFinder<A extends Annotation> {

	protected final Class<A> annotationType;
	
	protected final boolean repeatable;
	
	public AbstractAnnotationFinder(Class<A> annotationType) {
		this.annotationType = annotationType;
		repeatable = annotationType.isAnnotationPresent(Repeatable.class);
	}

}
