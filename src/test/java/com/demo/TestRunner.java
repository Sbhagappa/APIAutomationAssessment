package com.demo;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"},
        features = {"src/test/resources/feature"},
        glue = {"src/test/java/com/demo/step_definitions"}
        //tags = {"@PositiveTest"}
        //tags = {"@NegativeTest"}
)
public class TestRunner {

}
