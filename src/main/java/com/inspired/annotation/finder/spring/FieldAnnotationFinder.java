package com.inspired.annotation.finder.spring;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

import com.inspired.annotation.finder.AbstractAnnotationFinder;
import com.inspired.annotation.finder.IFieldAnnotationFinder;

import lombok.NonNull;

public class FieldAnnotationFinder<A extends Annotation> extends AbstractAnnotationFinder<A> implements IFieldAnnotationFinder<A> {

	public FieldAnnotationFinder(Class<A> annotationClass) {
		super(annotationClass);
	}

	@Override
	public Map<String, List<A>> onFieldsThisOnly(@NonNull Class<?> clazz) {
		Map<String, List<A>> annotationsByField = new HashMap<>();
		if (repeatable) {
			ReflectionUtils.doWithFields(clazz, new FieldCallback() {
				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					Set<A> found = AnnotationUtils.getDeclaredRepeatableAnnotations(field, annotationType);
					if (found == null || found.isEmpty()) {
						return;
					}
					annotationsByField.put(field.getName(), new ArrayList<A>(found));
				}			
			});
		} else {
			ReflectionUtils.doWithFields(clazz, new FieldCallback() {
				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					A found = AnnotationUtils.findAnnotation(field, annotationType);
					if (found == null) {
						return;
					}
					annotationsByField.put(field.getName(), Arrays.asList(found));
				}
			});
		}
		
		return annotationsByField;
	}
	
	@Override
	public Map<Class<?>, Map<String, List<A>>> onFieldsDeep(@NonNull Class<?> clazz) {
		Map<Class<?>, Map<String, List<A>>> annotationsByClassAndField = new HashMap<>();
		findOnFieldsDeep(clazz, annotationsByClassAndField);
		return annotationsByClassAndField;
	}
	
	private void findOnFieldsDeep(@NonNull Class<?> clazz, Map<Class<?>, @NonNull Map<String, List<A>>> annotationsByClassAndField) {
		if (annotationsByClassAndField.containsKey(clazz)) {
			return;
		}
		annotationsByClassAndField.put(clazz, onFieldsThisOnly(clazz));
		ReflectionUtils.doWithFields(clazz, new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				Class<?> fieldType = field.getType();
				if (Serializable.class.isAssignableFrom(fieldType)) {
					findOnFieldsDeep(fieldType, annotationsByClassAndField);
				} else if (fieldType.isArray()){
					Class<?> componentType = fieldType;
					while(componentType.isArray()) {
						componentType = fieldType.getComponentType();
					}
					if (!componentType.isPrimitive()) {
						findOnFieldsDeep(componentType, annotationsByClassAndField);
					}
				} 
			}	
		}, new FieldFilter() {
			@Override
			public boolean matches(Field field) {
				return Serializable.class.isAssignableFrom(field.getType()) || field.getType().isArray();
			}	
		});
	}
	
}
