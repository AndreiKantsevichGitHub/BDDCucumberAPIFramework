package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

public class StepDefinition extends Utils {

    RequestSpecification request;
    TestDataBuild data = new TestDataBuild();
    ResponseSpecification responseSpecification;
    Response response;
    static String placeID;

    @Given("Add Place Payload with {string} {string} {string}")
    public void addPlacePayloadWith(String name, String address, String language) throws IOException {

        request =
                RestAssured.given()
                        .spec(requestSpecification())
                        .body(data.addPlacePayload(name, address, language));
    }

    @When("user calls {string} with {string} http request")
    public void userCallsWithHttpRequest(String resource, String method) {

        APIResources resourcesAPI = APIResources.valueOf(resource);
        System.out.println(resourcesAPI.getResource());

        responseSpecification =
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .build();

        if (method.equalsIgnoreCase("Post"))
            response = request.when().post(resourcesAPI.getResource());
        else if (method.equalsIgnoreCase("Get"))
            response = request.when().get(resourcesAPI.getResource());
    }

    @Then("the APIs call get success with status code {int}")
    public void theAPIsCallGetSuccessWithStatusCode(int arg0) {
        Assert.assertEquals
                (response.getStatusCode(), 200);
    }

    @And("{string} in response body is {string}")
    public void inResponseBodyIs(String key, String value) {
       Assert.assertEquals
               (getJsonPath(response, key), value);
    }

    @And("verify placeID created map to {string} using {string}")
    public void verifyPlaceIDCreatedMapToUsing(String expectedName, String resource) throws IOException {
        placeID = getJsonPath(response, "place_id");
        request =
                RestAssured.given()
                        .spec(requestSpecification())
                        .queryParam("place_id", placeID);
        userCallsWithHttpRequest(resource, "GET");

        String actualName = getJsonPath(response, "name");
        Assert.assertEquals(expectedName, actualName);
    }

    @Given("Delete Place Payload")
    public void deletePlacePayload() throws IOException {
        request =
                RestAssured.given()
                        .spec(requestSpecification())
                        .body(data.deletePlacePayload(placeID));
    }
}
