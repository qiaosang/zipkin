/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lesports.albatross.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * ReflectionUtils - ReflectionUtils
 *
 * @author Vlad Mihalcea
 */
@Slf4j
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    @SuppressWarnings("rawtypes")
    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint pjp, Class<T> annotationClass) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        T annotation = AnnotationUtils.findAnnotation(method, annotationClass);

        if (annotation != null) {
            return annotation;
        }

        Class[] argClasses = new Class[pjp.getArgs().length];
        for (int i = 0; i < pjp.getArgs().length; i++) {
            argClasses[i] = pjp.getArgs()[i].getClass();
        }
        method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argClasses);
        return AnnotationUtils.findAnnotation(method, annotationClass);
    }

    public static Map<String, Object> retrieveGetterMethodValues(Object someObject) {
        Map<String, Object> propertiesAndValues = new HashMap<>();
        for (Method method : someObject.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))
                    ) {
                try {
                    Object value = method.invoke(someObject);
                    propertiesAndValues.put(method.getName(), value);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                    log.error("retrieveGetterMethodValues failed with exception: {}", ex);
                }
            }
        }
        return propertiesAndValues;
    }
}
