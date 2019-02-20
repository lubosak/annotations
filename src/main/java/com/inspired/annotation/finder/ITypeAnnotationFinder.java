package com.inspired.annotation.finder;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ITypeAnnotationFinder<A extends Annotation> {

	List<A> onType(Class<?> clazz);
	
	List<A> onTypeThisOnly(Class<?> clazz);
}
