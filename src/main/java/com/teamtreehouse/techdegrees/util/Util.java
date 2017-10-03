package com.teamtreehouse.techdegrees.util;

import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rumy on 10/2/2017.
 */
public class Util {

    public static TodoDt convertToTodoDt(Todo todo) {
        TodoDt todoDt=new TodoDt();
        todoDt.setId(todo.getId());
        todoDt.setName(todo.getName());
        todoDt.setCompleted(todo.isCompleted());
        return todoDt;
    }

    public static Todo convertToTodo(TodoDt todoDt){
        Todo todo=new Todo(todoDt.getName());
        todo.setId(todoDt.getId());
        todo.setCompleted(todoDt.isCompleted());
        return todo;
    }


    public static List<TodoDt> convertAllTodos(List<Todo> todos){
        return todos.stream()
                .map(todo->convertToTodoDt(todo))
                .collect(Collectors.toList());
    }
}
