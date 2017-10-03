package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.model.Todo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by Rumy on 9/20/2017.
 */
public class Sql2oTodoDaoTest {
    private Sql2oTodoDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString="jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o=new Sql2o(connectionString,"","");
        dao = new Sql2oTodoDao(sql2o);
        //keep connection open
        conn=sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        conn.close();

    }


    @Test
    public void addingTodoSetsId() throws Exception {
        Todo todo=new Todo("test");
        int origId=todo.getId();

        dao.add(todo);

        assertNotEquals(origId,todo.getId());
    }

    @Test
    public void addedTodosAreReturnedFromFindAll() throws Exception {
        Todo todo=new Todo("test");

        dao.add(todo);

        assertEquals(1,dao.findAll().size());

    }

    @Test
    public void existingTodosCanBeFoundById() throws Exception {
        Todo todo=new Todo("test");
        dao.add(todo);

        Todo foundTodo=dao.findById(todo.getId());

        assertEquals(todo,foundTodo);
    }


    @Test
    public void noTodosReturnEmptyList() throws Exception {
        assertEquals(0,dao.findAll().size());
    }

    @Test
    public void deletedTodoCannotBeFound() throws Exception {
        Todo todo=new Todo("test");
        dao.add(todo);
        int id=todo.getId();

        dao.delete(id);

        assertEquals(null,dao.findById(id));

    }


    @Test
    public void updatingTest() throws Exception {
        Todo todo=new Todo("test");
        dao.add(todo);
        todo.setName("New Test");

        dao.update(todo);

        assertEquals("New Test", todo.getName());
    }



}