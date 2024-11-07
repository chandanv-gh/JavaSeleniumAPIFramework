package com.testvagrant.executor;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/main/resources/Features/Test2.feature",
        glue = "com.testvagrant.stepdef",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)
public class Test2Runner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel=true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

