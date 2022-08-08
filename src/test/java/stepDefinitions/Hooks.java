package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        StepDefinition z = new StepDefinition();
        if (StepDefinition.placeID == null) {
            z.addPlacePayloadWith("Andrea", "Rome, Italy", "Italian");
            z.userCallsWithHttpRequest("AddPlaceAPI", "POST");
            z.verifyPlaceIDCreatedMapToUsing("Andrea", "GetPlaceAPI");
        }
    }
}
