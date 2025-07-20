import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class MyRestAssured {
    Properties prop;
    public MyRestAssured() throws IOException {
        prop=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
    }

    @Test
    public void doLogin() throws ConfigurationException, IOException {
        RestAssured.baseURI=prop.getProperty("baseUrl");
        Response res= given().contentType("application/json")
                .body("{\n" +
                        "    \"email\":\"admin@roadtocareer.net\",\n" +
                        "    \"password\":\"1234\"\n" +
                        "}")
                .when().post("/user/login");
        System.out.println(res.asString());

        JsonPath jsonobj=res.jsonPath();
        String token=jsonobj.get("token");
        Utils.setEnv("token",token);
    }
    @Test
    public void searchUser() throws IOException {
        RestAssured.baseURI=prop.getProperty("baseUrl");
        Response res=given().contentType("application/json").header("Authorization","bearer "+ prop.getProperty("token"))
                .when().get("/user/search/id/78690");
        System.out.println(res.asString());
    }
    @Test
    public void createUser() throws IOException {
        RestAssured.baseURI=prop.getProperty("baseUrl");
        Response res=given().contentType("application/json").header("Authorization","bearer " + prop.getProperty("token"))
                .body("{\n" +
                        "  \"name\": \"RA b15 user 26\",\n" +
                        "  \"email\": \"raba26@test.com\",\n" +
                        "  \"password\": \"1234\",\n" +
                        "  \"phone_number\":\"01521111133\",\n" +
                        "  \"nid\": \"9576525111\",\n" +
                        "  \"role\": \"Customer\"\n" +
                        "}")
                .header("X-AUTH-SECRET-KEY",prop.getProperty("partnerKey")).when().post("/user/create");
        System.out.println(res.asString());

    }

}
