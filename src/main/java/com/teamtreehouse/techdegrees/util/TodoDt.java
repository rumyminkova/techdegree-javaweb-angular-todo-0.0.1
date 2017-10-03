package com.teamtreehouse.techdegrees.util;

import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rumy on 10/2/2017.
 */
public class TodoDt {
    private int id;
    private String name;
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}



