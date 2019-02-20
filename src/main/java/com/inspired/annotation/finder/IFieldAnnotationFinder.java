package com.inspired.annotation.finder;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public interface IFieldAnnotationFinder<A extends Annotation> {

	Map<Class<?>, Map<String, List<A>>> onFieldsDeep(Class<?> clazz);
	
	Map<String, List<A>> onFieldsThisOnly(Class<?> clazz);
}
