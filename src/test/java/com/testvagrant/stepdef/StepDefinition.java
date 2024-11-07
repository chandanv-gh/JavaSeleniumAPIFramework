package com.testvagrant.stepdef;

import com.testvagrant.appGenericFunction.AppGenericFunc;
import com.testvagrant.appGenericFunction.DataFunc;
import com.testvagrant.payload.Product;
import com.testvagrant.utils.WebDriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;

import java.util.List;
import java.util.UUID;

public class StepDefinition {
    DataFunc df = new DataFunc();
    AppGenericFunc appGenFunc = new AppGenericFunc();
    String username;
    String password;


    @Given("I launch the browser")
    public void i_launch_the_browser() {
        appGenFunc.launchBrowser();
        df.getTests("TestCases");
    }

    @Given("I am on the {string} homepage")
    public void i_am_on_the_homepage(String app) {
        appGenFunc.launchUrl(df.getAppUrl(app));
    }

    @Then("I click on the {string} from the list and validate the response of {string} request")
    public void i_click_on_the_from_the_list_and_validate_the_response_of_request(String api, String requestType) throws Exception {
        String request = appGenFunc.clickOnButton(df.getXpath(api));
        appGenFunc.captureData(request);
        switch(requestType) {
            case "Get":
                appGenFunc.getRequest();
                break;
            case "Post":
                appGenFunc.postRequest();
                break;
        }
    }

    @Then("I capture all the Product details")
    public void i_capture_all_the_product_details() {
        appGenFunc.captureProductDetails();
    }

    @When("I click on the {string} link")
    public void i_click_on_the_link(String text) {
        appGenFunc.clickOnAnchorTag(text);
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

    @Then("I autogenerate {string} and {string}")
    public void i_autogenerate_and(String string, String string2) {
        username = UUID.randomUUID().toString().substring(0, 10);
        password = UUID.randomUUID().toString().substring(0, 8);
        df.updateJsonFile("registration", "Username", username);
        df.updateJsonFile("registration", "Password", password);
        df = new DataFunc();
    }

    @Then("I should see a confirmation message {string}")
    public void i_should_see_a_confirmation_message(String expectedMsg) {
        String actualMsg = appGenFunc.checkElementVisibility(df.getXpath("success"));
        if(actualMsg.contains(expectedMsg)) {
            System.out.println("Successfully Registered");
        }
        else {
            System.out.println("Registration is Unsuccessful");
        }
    }

    @When("I leave {string} blank")
    public void i_leave_blank(String textBox) {
        appGenFunc.setText(df.getXpath(textBox), "");
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedError) {
        appGenFunc.checkElementVisibility(By.xpath("//*[text()='"+expectedError+"']"));
    }

    @Then("I should see a welcome message {string}")
    public void i_should_see_a_welcome_message(String expectedMsg) {
        String actualMsg = appGenFunc.checkElementVisibility(df.getXpath("welcome"));
        if(actualMsg.equals(expectedMsg)) {
            System.out.println("Login successful");
        }
        else {
            System.out.println("Login failed");
        }
    }
    @Then("click on {string} button")
    public void click_on_button(String button) {
        appGenFunc.clickOnButton(df.getXpath(button));
    }
    @Then("I should see the {string} message")
    public void i_should_see_the_message(String msg) {
        appGenFunc.checkElementVisibility(df.getXpath(msg.toLowerCase()));
    }


    @Given("I enter {string} of the {string} user")
    public void i_enter_of_the_user(String input, String user) {
        String[] in = input.split(",");
        String username = "";
        String password = "";
        if(user.equals("Registered")) {
            username = df.getTestData(in[0].toLowerCase());
            password = df.getTestData(in[1].toLowerCase());
        }
        appGenFunc.setText(df.getXpath(in[0].toLowerCase()), username);
        appGenFunc.setText(df.getXpath(in[1].toLowerCase()), password);
    }

    @Then("I validate the {string} of the logged in user")
    public void i_validate_the_of_the_logged_in_user(String string) {
        appGenFunc.checkElementVisibility(By.xpath("//p[contains(text(),'"+df.getTestData("firstName")+" "+df.getTestData("lastName")+"')]"));
    }

    @Given("I validate all the elements in the {string} page")
    public void i_validate_all_the_elements_in_the_page(String page) {
        List<String> testElements = df.getList(page);
        for(String ele:testElements) {
            appGenFunc.checkElementVisibility(By.xpath("//a[text()='"+ele+"']"));
        }
    }


    @After
    public void endTest() {
        WebDriverManager.quitDriver();
    }
}
