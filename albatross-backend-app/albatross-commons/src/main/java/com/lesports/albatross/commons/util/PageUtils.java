package com.lesports.albatross.commons.util;

import com.lesports.albatross.commons.web.domain.PageableResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by Gang Li on 4/25/16.
 * Copyright © 2016 LeSports Inc. All rights reserved.
 */

public final class PageUtils {

    private PageUtils() {
    }

    public static <T> PageableResource<T> toResource(Page<T> page) {
        if (page == null || page.getContent() == null) return null;
        Pageable pageable = PageUtils.toPageable(page);
        return new PageableResource<>(new PageImpl<>(page.getContent(), pageable, page.getTotalElements()));
    }

    public static Pageable toPageable(Page<?> page) {
        return new Pageable() {
            @Override
            public int getPageNumber() {
                return page.getNumber();
            }

            @Override
            public int getPageSize() {
                return page.getSize();
            }

            @Override
            public int getOffset() {
                return getPageSize() * getPageNumber(); // return page.getNumberOfElements() + getPageSize() * getPageNumber();
            }

            @Override
            public Sort getSort() {
                return page.getSort();
            }

            @Override
            public Pageable next() {
                return page.nextPageable();
            }

            @Override
            public Pageable previousOrFirst() {
                return page.previousPageable();
            }

            @Override
            public Pageable first() {
                if (page.isFirst()) {
                    return this;
                }
                return page.previousPageable();
            }

            @Override
            public boolean hasPrevious() {
                return page.hasPrevious();
            }
        };
    }
}
