package cucmber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions
        (
                features = "src/test/java/features"
                ,glue = {"stepDefinitions"}
                ,plugin = {"html:target/cucumber-html-report",
                "json:target/jsonReports/cucumber-report.json"}
                ,tags = "@Regression"
        )
public class TestRunner {
}
