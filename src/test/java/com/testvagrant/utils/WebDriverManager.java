package com.testvagrant.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {
     private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
             public static WebDriver getDriver() {
                 if (driver.get() == null) {
//                         System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
                         driver.set(new ChromeDriver());
                     }
                 return driver.get();
             }

             public static void quitDriver() {
                 if (driver.get() != null) {
                         driver.get().quit();
                         driver.remove();
                     }
             }
}
