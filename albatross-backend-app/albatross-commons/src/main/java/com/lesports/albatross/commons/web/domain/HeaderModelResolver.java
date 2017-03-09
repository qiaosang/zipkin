package com.lesports.albatross.commons.web.domain;

import java.io.Serializable;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.WebRequest;


public interface HeaderModelResolver<T extends Serializable> {
    Optional<T> resolve(HttpServletRequest servletRequest);

    Optional<T> resolve(WebRequest webRequest);
}
