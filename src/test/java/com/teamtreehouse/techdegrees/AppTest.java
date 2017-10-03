package com.teamtreehouse.techdegrees;

import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegrees.dao.TodoDao;
import com.teamtreehouse.techdegrees.model.Todo;
import com.teamtreehouse.testing.ApiClient;
import com.teamtreehouse.testing.ApiResponse;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Rumy on 9/27/2017.
 */
public class AppTest {

    public static final String PORT = "4568";
    public static final String TEST_DATASOURCE = "jdbc:h2:mem:testing";
    private Connection conn;
    private ApiClient client;
    private Gson gson;
    private Sql2oTodoDao dao;

    @BeforeClass
    public static void startServer(){
        String[] args={PORT, TEST_DATASOURCE};
        App.main(args);
    }


    @AfterClass
    public  static void stopServer(){
        Spark.stop();
    }

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o=new Sql2o(TEST_DATASOURCE+";INIT=RUNSCRIPT from 'classpath:db/init.sql'","","");
        dao = new Sql2oTodoDao(sql2o);
        conn= sql2o.open();
        client=new ApiClient("http://localhost:"+PORT);
        gson=new Gson();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }


    @Test
    public void addingTodoReturnsCreatedStatus() throws Exception {
        Map<String,String> values=new HashMap<>();
        values.put("name","Test");

        ApiResponse res=client.request("POST","/api/v1/todos",gson.toJson(values));

        assertEquals(201,res.getStatus());

    }


    @Test
    public void allAddedTodosAreReturnedFromFindAll() throws Exception {
        Todo todo=new Todo("test");
        dao.add(todo);
        Todo todo1=new Todo("test1");
        dao.add(todo1);

        ApiResponse res=client.request("GET","/api/v1/todos");

        Todo[] retrievedTodos =gson.fromJson(res.getBody(),Todo[].class);

        assertEquals(2,retrievedTodos.length);

    }


    @Test
    public void todosCanBeAccessedById() throws Exception {
        Todo todo=new Todo("test");
        dao.add(todo);

        ApiResponse res=client.request("GET","/api/v1/todos/"+todo.getId());

        Todo retrievedTodo =gson.fromJson(res.getBody(),Todo.class);

        assertEquals(todo,retrievedTodo);
    }

    @Test
    public void missingTodosReturnNotFoundStatus() throws Exception {
        ApiResponse res=client.request("GET","/api/v1/todos/1000");

        assertEquals(404,res.getStatus());

    }


    @Test
    public void updateTodoNameTest() throws Exception {
        Todo todo=new Todo("test");
        dao.add(todo);
        String id=String.valueOf(todo.getId());
        String newName="NewTest";
        String compl=String.valueOf(todo.isCompleted());

        Map<String,String> values=new HashMap<>();
        values.put("id",id);
        values.put("name",newName);
        values.put("IsCompleted",compl);


        ApiResponse res=client.request("PUT","/api/v1/todos/"+id,gson.toJson(values));
        Todo retrievedTodo =gson.fromJson(res.getBody(),Todo.class);

        assertEquals("NewTest",retrievedTodo.getName());

    }

    @Test
    public void deleteTodoTest() throws Exception {
        Todo todo=new Todo("test");
        dao.add(todo);

        ApiResponse res=client.request("DELETE","/api/v1/todos/"+todo.getId());
        Todo retrievedTodo =gson.fromJson(res.getBody(),Todo.class);

     //   assertEquals(null,retrievedTodo);
        assertEquals(200,res.getStatus());

    }

  }