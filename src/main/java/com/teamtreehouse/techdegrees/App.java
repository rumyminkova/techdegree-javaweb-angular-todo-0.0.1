package com.teamtreehouse.techdegrees;


import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegrees.dao.TodoDao;
import com.teamtreehouse.techdegrees.exc.AppError;
import com.teamtreehouse.techdegrees.model.Todo;
import com.teamtreehouse.techdegrees.util.TodoDt;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.teamtreehouse.techdegrees.util.Util.convertAllTodos;
import static com.teamtreehouse.techdegrees.util.Util.convertToTodo;
import static com.teamtreehouse.techdegrees.util.Util.convertToTodoDt;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        String datasource = "jdbc:h2:./data/todos.db";
        if (args.length > 0) {
            if (args.length != 2) {
                System.out.println("java App <port> <datasource>");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
            datasource = args[1];
        }

        Sql2o sql2o = new Sql2o(datasource + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");

        TodoDao todoDao = new Sql2oTodoDao(sql2o);
        Gson gson = new Gson();

        get("/api/v1/todos", "application/json", (req, res) -> convertAllTodos(todoDao.findAll()), gson::toJson);


        get("/api/v1/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = todoDao.findById(id);
            if (todo == null) {
                throw new AppError(404, "Cannot find todo with id: " + id);
            }
            return convertToTodoDt(todo);
        }, gson::toJson);


        post("/api/v1/todos", "application/json", (req, res) -> {
            TodoDt todoDt = gson.fromJson(req.body(), TodoDt.class);
            Todo todo=convertToTodo(todoDt);
            todoDao.add(todo);
            res.status(201);
            return convertToTodoDt(todo);
        }, gson::toJson);


        put("/api/v1/todos/:id", "application/json", (req, res) -> {
            TodoDt todoDt = gson.fromJson(req.body(), TodoDt.class);
            Todo todo =convertToTodo(todoDt);
            todoDao.update(todo);
            res.status(200);
            return convertToTodoDt(todo);
        }, gson::toJson);

        delete("/api/v1/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            todoDao.delete(id);
            res.status(200);
            return null;
        }, gson::toJson);

        exception(AppError.class, (exc, req, res) -> {
            AppError err = (AppError) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatus());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatus());
            res.body(gson.toJson(jsonMap));

        });

        after(((req, res) -> res.type("application/json")));

    }

}
