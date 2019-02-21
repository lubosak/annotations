package com.inspired.annotation.finder.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inspired.annotation.finder.AbstractAnnotationFinder;
import com.inspired.annotation.finder.IFieldAnnotationFinder;

public class FieldAnnotationFinder<A extends Annotation> extends AbstractAnnotationFinder<A> implements IFieldAnnotationFinder<A> {

	public FieldAnnotationFinder(Class<A> annotationType) {
		super(annotationType);
	}

	@Override
	public Map<String, List<A>> onFieldsThisOnly(Class<?> clazz) {
		Map<String, List<A>> annotationsByField = new HashMap<>();
		try {
			for (PropertyDescriptor property : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
				A[] annotations = property.getReadMethod().getAnnotationsByType(annotationType);
				if (annotations == null || annotations.length == 0) {
					continue;
				}
				annotationsByField.put(property.getName(), Arrays.asList(annotations));
			}
		} catch (IntrospectionException e) {
			//log error
			return Collections.emptyMap();
		}
		return annotationsByField;
	}
	
	@Override
	public Map<Class<?>, Map<String, List<A>>> onFieldsDeep(Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}



}
