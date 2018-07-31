package com.reljicd.util;

import org.springframework.data.domain.Page;

/**
 * @author Dusan Raljic
 */
public class Pager<T> {

    private final Page<T> posts;

    public Pager(Page<T> posts) {
        this.posts = posts;
    }

    public int getPageIndex() {
        return posts.getNumber() + 1;
    }

    public int getPageSize() {
        return posts.getSize();
    }

    public boolean hasNext() {
        return posts.hasNext();
    }

    public boolean hasPrevious() {
        return posts.hasPrevious();
    }

    public int getTotalPages() {
        return posts.getTotalPages();
    }

    public long getTotalElements() {
        return posts.getTotalElements();
    }

    public Page<T> getPosts() {
        return posts;
    }

    public boolean indexOutOfBounds() {
        return getPageIndex() < 0 || getPageIndex() > getTotalElements();
    }

}
