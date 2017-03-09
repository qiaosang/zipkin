package com.lesports.albatross.commons.trace;

import java.lang.annotation.*;

/**
 * Created by Gang Li on 2016/7/8.
 * Copyright (c) 2016 LeSports Inc. All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface TracingPoint {
    String name() default "";

    String marker() default "***";

    String message() default "";

    LogLevel level() default LogLevel.TRACE;

    enum LogLevel {
        FATAL,
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE;
    }
}
