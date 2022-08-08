package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {

    private static RequestSpecification requestSpecification;

    public RequestSpecification requestSpecification() throws IOException {

        if (requestSpecification == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            requestSpecification =
                    new RequestSpecBuilder()
                            .setBaseUri(getGlobalValue("baseURI"))
                            .addQueryParam("key", "qaclick123")
                            .addFilter(RequestLoggingFilter.logRequestTo(log))
                            .addFilter(ResponseLoggingFilter.logResponseTo(log))
                            .setContentType(ContentType.JSON)
                            .build();
            return requestSpecification;
        }
        return requestSpecification;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\PC A\\IdeaProjects\\RAHUL SHETTY\\BDDCucumberAPIFramework\\src\\test\\java\\resources\\global.properties");
        properties.load(fileInputStream);
        return properties.getProperty(key);
    }

    public static String getJsonPath(Response response, String key) {
        String resp = response.asString();
        JsonPath jsonPath = new JsonPath(resp);
        return jsonPath.getString(key);
    }
}
