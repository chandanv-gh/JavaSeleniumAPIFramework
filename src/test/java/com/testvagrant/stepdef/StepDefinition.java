package com.testvagrant.stepdef;

import com.testvagrant.appGenericFunction.AppGenericFunc;
import com.testvagrant.appGenericFunction.DataFunc;
import com.testvagrant.payload.Product;
import com.testvagrant.utils.WebDriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StepDefinition {
    DataFunc df = new DataFunc();
    AppGenericFunc appGenFunc = new AppGenericFunc();
    String price;
    String productName;
    List<String> products;


    @Given("I launch the browser")
    public void i_launch_the_browser() {
        appGenFunc.launchBrowser();
        df.getTests("TestCases");
        products = new ArrayList<>();
    }

    @Given("I am on the {string} homepage")
    public void i_am_on_the_homepage(String app) {
        appGenFunc.launchUrl(df.getAppUrl(app));
    }

    @Then("I click on the {string} from the list and validate the response of {string} request")
    public void i_click_on_the_from_the_list_and_validate_the_response_of_request(String api, String requestType) throws Exception {
        String request = appGenFunc.clickOnButton(df.getXpath(api));
        appGenFunc.captureData(request);
        Response response = null;
        switch(requestType) {
            case "Get":
                response = appGenFunc.getRequest();
                break;
            case "Post":
                response = appGenFunc.postRequest();
                break;
            case "Put":
                response = appGenFunc.putRequest();
                break;
            case "Delete":
                response = appGenFunc.deleteRequest();
                break;
        }
        appGenFunc.validateApiResponse(response);
    }

    @Then("I capture all the Product details")
    public void i_capture_all_the_product_details() {
        appGenFunc.captureProductDetails();
    }

    @When("I click on the {string} link")
    public void i_click_on_the_link(String text) {
        appGenFunc.clickOnAnchorTag(text);
    }

    @Then("I should see {string} option")
    public void i_should_see_option(String element) {
        By xpath = By.xpath("//a[contains(text(),'"+element+"')]");
        appGenFunc.checkElementVisibility(xpath);
    }

    @Then("I should see {int} slides in the {string}")
    public void i_should_see_slides_in_the(Integer count, String ele) {
        appGenFunc.visibilityOfMultipleElements(count, df.getXpath(ele));
    }

    @Then("I should see {int} {string} in {string} section")
    public void i_should_see_in_section(Integer count, String items, String ele) {
        appGenFunc.visibilityOfMultipleElements(count, df.getXpath(ele));
    }

    @Then("I should see {string} textbox")
    public void i_should_see_textbox(String textbox) {
        appGenFunc.checkElementVisibility(df.getXpath(textbox));
    }

    @Then("I should see {string} button")
    public void i_should_see_button(String button) {
        appGenFunc.checkElementVisibility(df.getXpath(button));
    }

    @Then("I navigate to {string} Category and choose {string} Subcategory")
    public void i_navigate_to_category_and_choose_subcategory(String category, String subcategory) {
        appGenFunc.clickOnButton(df.getXpath(category));
        appGenFunc.clickOnButton(df.getXpath(subcategory));
    }

    @Then("I should see {string} under products")
    public void i_should_see_under_products(String pName) {
        appGenFunc.checkElementVisibility(df.getXpath(pName));
        productName = pName;
    }

    @Then("I verify the product details")
    public void i_verify_the_product_details() {
        price = appGenFunc.getText(By.xpath("//p[text()='"+productName+"']/parent::div[@class='productinfo text-center']/h2"));
        appGenFunc.checkElementVisibility(By.xpath("//p[text()='"+productName+"']/parent::div[@class='productinfo text-center']//a[@class='btn btn-default add-to-cart']"));
        appGenFunc.checkElementVisibility(By.xpath("//p[text()='"+productName+"']/ancestor::div[@class='product-image-wrapper']//a[text()='View Product']"));
    }

    @Then("I hover over the product")
    public void i_hover_over_the_product() {
        appGenFunc.hoverOverElement(By.xpath("//p[text()='"+productName+"']"));
        appGenFunc.checkElementVisibility(By.xpath("//p[text()='"+productName+"']/parent::div[@class='overlay-content']/h2"));
        appGenFunc.checkElementVisibility(By.xpath("//p[text()='"+productName+"']/parent::div[@class='overlay-content']/a[@class='btn btn-default add-to-cart']"));
    }

    @Then("I add {string} to cart")
    public void i_add_to_cart(String product) {
        appGenFunc.clickOnButton(By.xpath("//p[text()='"+product+"']/parent::div[@class='productinfo text-center']//a[@class='btn btn-default add-to-cart']"));
        products.add(product);
        appGenFunc.checkElementVisibility(By.xpath("//h4[text()='Added!']"));
    }

    @Then("I click on {string} button")
    public void i_click_on_button(String button) {
        appGenFunc.clickOnButton(df.getXpath(button));
    }

    @Then("I verify the products in cart")
    public void i_verify_the_products_in_cart() {
        for(String product:products) {
            appGenFunc.checkElementVisibility(By.xpath("//div[@class='table-responsive cart_info']//a[text()='"+product+"']"));
        }
    }

    @When("I fill in {string} with {string}")
    public void i_fill_in_with(String field, String input) {
        if(input.equals("testData")) {
            input = df.getTestData(field);
        }
        if(field.equals("Confirm Password")) {
            input = df.getTestData("Password");
        }
        appGenFunc.setText(df.getXpath(field), input);
    }

    @When("I enter {string} as the {string}")
    public void i_enter_as_the_username(String input, String field) {
        if(input.equals("testData")) {
            input = df.getTestData(field);
        }
        appGenFunc.setText(By.xpath("//input[@name='"+field.toLowerCase()+"']"), input);
    }

    @When("I click the {string} button")
    public void i_click_the_button(String button) {
        appGenFunc.clickOnButton(df.getXpath(button));
    }

    @When("I leave {string} blank")
    public void i_leave_blank(String textBox) {
        appGenFunc.setText(df.getXpath(textBox), "");
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedError) {
        appGenFunc.checkElementVisibility(By.xpath("//*[text()='"+expectedError+"']"));
    }

    @Then("click on {string} button")
    public void click_on_button(String button) {
        appGenFunc.clickOnButton(df.getXpath(button));
    }
    @Then("I should see the {string} message")
    public void i_should_see_the_message(String msg) {
        appGenFunc.checkElementVisibility(df.getXpath(msg.toLowerCase()));
    }

    @After
    public void endTest() {
        WebDriverManager.quitDriver();
    }
}
