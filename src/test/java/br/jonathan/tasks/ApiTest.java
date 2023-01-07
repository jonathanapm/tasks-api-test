package br.jonathan.tasks;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://192.168.100.114:8081/tasks-backend";
    }

    @Test
    public void shouldReturnTasks() {
        RestAssured.given()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldAddTaskSuccess() {
        RestAssured.given()
                    .body("{ \"task\": \"Teste via api\", \"dueDate\": \"2023-12-30\" }")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void shouldNotInvalidTask() {
        RestAssured.given()
                    .body("{ \"task\": \"Teste via api\", \"dueDate\": \"2022-12-30\" }")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}


