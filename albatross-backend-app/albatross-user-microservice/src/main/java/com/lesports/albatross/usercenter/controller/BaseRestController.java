package com.lesports.albatross.usercenter.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseRestController {

    private static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
