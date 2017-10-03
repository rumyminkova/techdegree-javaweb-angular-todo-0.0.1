package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;

/**
 * Created by Rumy on 9/19/2017.
 */
public interface TodoDao {
    void add(Todo todo) throws DaoException;

    List<Todo> findAll();

    Todo findById(int id);

    void delete(int id);

    void update(Todo todo);

}
