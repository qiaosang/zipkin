package com.lesports.albatross.commons.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * Created by litzuhsien on 6/16/15.
 */
@SuppressWarnings("unused")
public class PageableResource<T> extends ResourceSupport implements Page<T> {

    private final Page<T> page;

    public PageableResource(Page<T> page, String pageParam, String sizeParam) {
        super();
        this.page = page;
        addPreviousLink(this.page, pageParam, sizeParam);
        addNextLink(this.page, pageParam, sizeParam);
        addSelfLink(this.page, pageParam, sizeParam);
    }

    public PageableResource(Page<T> page) {
        this.page = page;
        addPreviousLink(this.page, "page", "size");
        addNextLink(this.page, "page", "size");
        addSelfLink(this.page, "page", "size");
    }

    //@JsonProperty("_links")
    @JsonIgnore
    public List<Link> getLinks() {
        return super.getLinks();
    }

    private void addPreviousLink(Page<T> page, String pageParam,
                                 String sizeParam) {
        if (page.hasPrevious()) {
            Link link = buildPageLink(pageParam, page.getNumber() - 1, sizeParam, page.getSize(), Link.REL_PREVIOUS);
            add(link);
        }
    }

    private void addNextLink(Page<T> page, String pageParam,
                             String sizeParam) {
        if (page.hasNext()) {
            Link link = buildPageLink(pageParam, page.getNumber() + 1, sizeParam, page.getSize(), Link.REL_NEXT);
            add(link);
        }
    }

    private void addSelfLink(Page<T> page, String pageParam,
                             String sizeParam) {
        Link link = buildPageLink(pageParam, page.getNumber(), sizeParam, page.getSize(), Link.REL_SELF);
        add(link);
    }

    private void addFirstLink(Page<T> page, String pageParam,
                              String sizeParam) {
        Link link = buildPageLink(pageParam, 0, sizeParam, page.getSize(), Link.REL_FIRST);
        add(link);
    }


    private void addLastLink(Page<T> page, String pageParam,
                             String sizeParam) {
        Link link = buildPageLink(pageParam, page.getTotalPages() - 1, sizeParam, page.getSize(), Link.REL_LAST);
        add(link);
    }

    private Link buildPageLink(String pageParam, int page, String sizeParam, int size, String rel) {
        String path = createBuilder()
                .queryParam(pageParam, page)
                .queryParam(sizeParam, size)
                .build()
                .toUriString();
        Link link = new Link(path, rel);
        return link;
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

    /////////
    @Override
    public Iterator<T> iterator() {
        return page.iterator();
    }

    @Override
    @JsonProperty("_total_pages")
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    @JsonProperty("_total")
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    @JsonIgnore
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return null;
    }

    @Override
    @JsonProperty("_page")
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    @JsonProperty("_size")
    public int getSize() {
        return page.getSize();
    }

    @Override
    @JsonProperty("_content_size")
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    @JsonProperty("_content")
    public List<T> getContent() {
        return page.getContent();
    }

    @Override
    @JsonIgnore
    public boolean hasContent() {
        return page.hasContent();
    }

    @Override
    @JsonIgnore
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    @JsonIgnore
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    @JsonIgnore
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    @JsonProperty("_has_next")
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    @JsonProperty("_has_prev")
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    @JsonIgnore
    public Pageable nextPageable() {
        return page.nextPageable();
    }

    @Override
    @JsonIgnore
    public Pageable previousPageable() {
        return page.previousPageable();
    }
}
