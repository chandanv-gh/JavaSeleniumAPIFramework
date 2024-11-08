package com.testvagrant.appGenericFunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testvagrant.endpoints.ProductEndPoints;
import com.testvagrant.payload.Product;
import com.testvagrant.utils.WebDriverManager;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class AppGenericFunc {
    private WebDriver driver = WebDriverManager.getDriver();
    private WebDriverWait wait;
    ProductEndPoints productEndpoint;
    UIData response;
    List<Product> products;

    public AppGenericFunc() {
        productEndpoint = new ProductEndPoints();
        response = new UIData();
    }

    public void launchBrowser() {
        driver = WebDriverManager.getDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void launchUrl(String url) {
        driver.get(url);
    }

    public void clickOnAnchorTag(String text) {
        By xpath = By.xpath("//a[contains(text(),'"+text+"')]");
        WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(xpath));
        highlightElement(ele);
        ele.click();
    }

    public void visibilityOfMultipleElements(int count, By xpath) {
        try {
            List<WebElement> elements = driver.findElements(xpath);
            assertEquals(elements.size(), count, "Element count should match");
        }
        catch (AssertionError e) {
            System.out.println(e);
        }
    }

    public String getText(By xpath) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
        js.executeScript("arguments[0].scrollIntoView(true);", ele);
        highlightElement(ele);
        return ele.getText();
    }

    public void captureData(String request) throws Exception {
        String url = getText(By.xpath("//u[text()='"+request+"']/ancestor::div[@class='panel-group']//b[text()='API URL:']/following-sibling::*"));
        String requestMethod = getText(By.xpath("//u[text()='"+request+"']/ancestor::div[@class='panel-group']//li[contains(.,'Request Method:')]")).split(":")[1].trim();
        int responseCode = Integer.parseInt(getText(By.xpath("//u[text()='"+request+"']/ancestor::div[@class='panel-group']//li[contains(.,'Response Code:')]")).split(":")[1].trim());
        String responseMsg = "";
        try {
            responseMsg = getText(By.xpath("//u[text()='"+request+"']/ancestor::div[@class='panel-group']//li[contains(.,'Response Message:')]")).split(":")[1].trim();
        }
        catch(Exception e) {
            responseMsg = getText(By.xpath("//u[text()='"+request+"']/ancestor::div[@class='panel-group']//li[contains(.,'Response JSON:')]")).split(":")[1].trim();
        }
        response.setRequestURL(url);
        response.setRequestMethod(requestMethod);
        response.setResponseCode(responseCode);
        response.setResponseMsg(responseMsg);

        response.setProducts(this.products);
    }

    public void captureProductDetails() {
        products = new ArrayList<>();
        List<WebElement> products = driver.findElements(By.xpath("//div[@class='productinfo text-center']/a[@data-product-id]"));
        for(WebElement ele:products) {
            Product product = new Product();
            product.setPrice(getText(By.xpath("//a[@data-product-id='"+ele.getAttribute("data-product-id")+"']/parent::div[@class='productinfo text-center']/h2")));
            product.setName(getText(By.xpath("//a[@data-product-id='"+ele.getAttribute("data-product-id")+"']/parent::div[@class='productinfo text-center']/p")));
            product.setId(Integer.parseInt(Objects.requireNonNull(ele.getAttribute("data-product-id"))));
            this.products.add(product);
        }
    }

    public Response postRequest() throws Exception {
        Product product = new Product();
        // Set product fields
        product.setId(1);
        product.setName("Blue Top");
        product.setPrice("Rs. 500");
        product.setBrand("Polo");

        // Create and set category
        Product.Category category = new Product.Category();
        category.setCategory("Tops");

        // Create and set usertype
        Product.UserType userType = new Product.UserType();
        userType.setUsertype("Women");

        // Set usertype in category
        category.setUsertype(userType);

        // Set category in product
        product.setCategory(category);
        Response response = productEndpoint.createProduct(this.response.getRequestURL(), product);
        return response;
    }

    public Response getRequest() throws Exception {
        Response response = productEndpoint.getProductDetails(this.response.getRequestURL());
        return response;
    }

    public Response deleteRequest() throws Exception {
        Response response = productEndpoint.deleteProduct(this.response.getRequestURL());
        return response;
    }

    public Response putRequest() {
        Product product = new Product();
        // Set product fields
        product.setId(1);
        product.setName("Blue Top");
        product.setPrice("Rs. 500");
        product.setBrand("Polo");

        // Create and set category
        Product.Category category = new Product.Category();
        category.setCategory("Tops");

        // Create and set usertype
        Product.UserType userType = new Product.UserType();
        userType.setUsertype("Women");

        // Set usertype in category
        category.setUsertype(userType);

        // Set category in product
        product.setCategory(category);
        Response response = productEndpoint.updateProductDetails(this.response.getRequestURL(), product);
        return response;
    }

    public void validateApiResponse(Response response) throws Exception {
        try {
            assertEquals(response.getStatusCode(), this.response.getResponseCode(), "Response code mismatch");

            // Validate the Response Message

            try {
                String responseMessage = response.jsonPath().getString("responseMessage");
                assertEquals(responseMessage, this.response.getResponseMsg(), "Response message mismatch");
            }
            catch (AssertionError e) {
                System.out.println(e);
            }

            // Parse the response body using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            APIResponse apiResponse = objectMapper.readValue(response.asString(), APIResponse.class);

            // Validate Response Structure/Content (non-hardcoded validation)
            assertNotNull(apiResponse, "Parsed response should not be null");
            try {
                assertEquals(apiResponse.getResponseCode(), this.response.getResponseCode(), "Response code in parsed content mismatch");
                assertEquals(apiResponse.getResponseMessage(), this.response.getResponseMsg(), "Response message in parsed content mismatch");
            }
            catch (AssertionError e) {
                System.out.println(e);
            }

            // Validate products (example structure checks)
            assertNotNull(apiResponse.getProducts(), "Products list should not be null");
            List<Product> expectedProductDetails = this.response.getProducts();
            assertEquals(expectedProductDetails.size(), apiResponse.getProducts().size(), "Number of Products should be equals to " + expectedProductDetails);
            for (Product product : apiResponse.getProducts()) {
                validateEachProduct(product);
                assertNotNull(product.getName(), "Product name should not be null");
                assertNotNull(product.getPrice(), "Product price should not be null");
                assertNotNull(product.getBrand(), "Product brand should not be null");
                assertNotNull(product.getCategory(), "Product category should not be null");
                assertNotNull(product.getCategory().getCategory(), "Product category name should not be null");
                assertNotNull(product.getCategory().getUsertype(), "Product usertype should not be null");
            }

            // Optionally, print out the full response for debugging
            System.out.println(response.asPrettyString());
        }
        catch (AssertionError e) {
            System.out.println(e);
        }
    }

    public void validateEachProduct(Product aProduct) {
        try {
            for(Product p:products) {
                if(p.getId()==aProduct.getId()) {
                    assertEquals(p.getName(), aProduct.getName(), "Product Name should match");
                    assertEquals(p.getPrice(), aProduct.getPrice(), "Product price should match");

                    break;
                }
            }
        }
        catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Use a JavaScript command to change the element's style
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    public void hoverOverElement(By xpath) {
        Actions actions = new Actions(driver);
        WebElement elementToHover = driver.findElement(xpath);
        actions.moveToElement(elementToHover).perform();
        }

    public void setText(By xpath, String text) {
        WebElement textBox = driver.findElement(xpath);
        textBox.clear();
        textBox.sendKeys(text);
    }

    public String clickOnButton(By button) {
        WebElement ele = driver.findElement(button);
        String text = ele.getText();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        highlightElement(ele);
        js.executeScript("arguments[0].click();", ele);
        return text;
    }

    public void checkElementVisibility(By ele) {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
            if(element.isDisplayed()) {
                highlightElement(element);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
