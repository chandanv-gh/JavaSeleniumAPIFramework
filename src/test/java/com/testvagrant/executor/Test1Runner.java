package com.testvagrant.executor;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/Features/Test1.feature",
        glue = "com.testvagrant.stepdef",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)
public class Test1Runner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel=false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

