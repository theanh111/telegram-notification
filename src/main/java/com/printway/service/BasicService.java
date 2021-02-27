package com.printway.service;

public interface BasicService<T> {
    public T findById(Integer id);
    public T update(T t);
    public T create(T t);
}
