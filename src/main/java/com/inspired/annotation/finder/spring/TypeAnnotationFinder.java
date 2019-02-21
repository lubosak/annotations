package com.inspired.annotation.finder.spring;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.core.annotation.AnnotationUtils;

import com.inspired.annotation.finder.AbstractAnnotationFinder;
import com.inspired.annotation.finder.ITypeAnnotationFinder;

public class TypeAnnotationFinder<A extends Annotation> extends AbstractAnnotationFinder<A> implements ITypeAnnotationFinder<A> {

	public TypeAnnotationFinder(Class<A> annotationType) {
		super(annotationType);
	}

	@Override
	public List<A> onTypeThisOnly(Class<?> clazz) {
		if (repeatable) {
			Set<A> found = AnnotationUtils.getDeclaredRepeatableAnnotations(clazz, annotationType);
			return new ArrayList<A>(found);
		} else {
			A found = AnnotationUtils.getAnnotation(clazz, annotationType);
			return Arrays.asList(found);
		}
	}
	
	@Override
	public List<A> onType(Class<?> clazz) {
		if (repeatable) {
			Set<A> found = AnnotationUtils.getRepeatableAnnotations(clazz, annotationType);
			return new ArrayList<A>(found);
		} else {
			A found = AnnotationUtils.findAnnotation(clazz, annotationType);
			return Arrays.asList(found);
		}
	}
	
}
