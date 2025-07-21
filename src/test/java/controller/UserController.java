package controller;

import config.UserModel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;

    public UserController(Properties prop){
        this.prop=prop;
        RestAssured.baseURI=prop.getProperty("baseUrl");

    }
    public Response doLogin(UserModel userModel){
        Response res= given().contentType("application/json")
                .body(userModel)
                .when().post("/user/login");
        return res;
    }
    public Response createUser(UserModel userModel){
        return given().contentType("application/json").header("Authorization","bearer " + prop.getProperty("token"))
                .body(userModel)
                .header("X-AUTH-SECRET-KEY",prop.getProperty("partnerKey")).when().post("/user/create");
//        return res;
    }
    public Response searchUser(String userId){
        return given().contentType("application/json").header("Authorization","bearer " + prop.getProperty("token"))
                .when().get("/user/search/id/" +userId);
    }
    public Response deleteUser(String userId){
        return given().contentType("application/json")
                .header("Authorization","bearer " +prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY",prop.getProperty("partnerKey")).when().delete("/user/delete/"+userId);
    }
}
