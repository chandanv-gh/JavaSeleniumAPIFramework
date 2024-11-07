package com.testvagrant.endpoints;

import com.testvagrant.payload.Product;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductEndPoints {
    public Response createProduct(String url, Product payload) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(url)
                .then()
                .statusCode(200) // Validate that the response code is 200
                .extract()
                .response();
    }

    public Response getProductDetails(String url) {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .get(url) // Replace with the actual path of your API
                .then()
                .statusCode(200) // Validate that the response code is 200
                .extract()
                .response();
    }
}
