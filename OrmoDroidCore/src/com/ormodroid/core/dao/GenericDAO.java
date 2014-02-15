package com.ormodroid.core.dao;

import java.util.List;
import java.util.Map;

import com.ormodroid.core.exception.ObjectNotFoundException;

public interface GenericDAO<T> {

    T get(Object id) throws ObjectNotFoundException;
    
    T get(String column, String value) throws ObjectNotFoundException;
    
    T get(Map<String, String> values) throws ObjectNotFoundException;
    
    List<T> find(String column, String value);
    
    List<T> find(Map<String, String> values);

    List<T> all();
    
    long count();
    
    long count(String column, String value);
    
    long count(Map<String, String> values);
    
    long save(T object);
    
    int delete(Object id);
    
    int update(T object, Object id);
    
}