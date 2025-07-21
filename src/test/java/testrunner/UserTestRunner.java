package testrunner;

import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

public class UserTestRunner extends Setup {

    UserController userController;

    @BeforeClass
    public void createUserControllerobj(){
         userController=new UserController(prop);
    }

    @Test(priority = 1,description = "User Login")
    public void doLogin() throws ConfigurationException {
        UserModel userModel=new UserModel();
        userModel.setEmail("admin@roadtocareer.net");
        userModel.setPassword("1234");
        Response res=userController.doLogin(userModel);
        System.out.println(res.asString());
        JsonPath jsonObj=res.jsonPath();
        String token=jsonObj.get("token");
        Utils.setEnv("token",token);
    }
    @Test(priority = 2,description = "Create User")
    public void createUser() throws ConfigurationException {
        UserModel userModel=new UserModel();
        Faker faker=new Faker();
        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress());
        userModel.setPassword("1234");
        String phoneNumber="013"+Utils.generateRandomNumber(10000000,99999999);
        userModel.setPhone_number(phoneNumber);
        userModel.setNid("123456789");
        userModel.setRole("Customer");
        Response res=userController.createUser(userModel);
        System.out.println(res.asString());
        JsonPath jsonObject=res.jsonPath();
        int userId=jsonObject.get ("user.id");
        System.out.println(userId);
        Utils.setEnv("userId",String.valueOf(userId));
    }
    @Test(priority = 3 , description = "Search User")
    public void searchUser(){
        Response res =userController.searchUser(prop.getProperty("userId"));
        System.out.println(res.asString());

        JsonPath jsonPath=res.jsonPath();
        String messageActual=jsonPath.get("message");
        String messageExpected="User found";
        Assert.assertEquals(messageActual,messageExpected);
    }
    @Test(priority = 4,description = "Delete user")
    public void userDelete(){
        Response res=userController.deleteUser(prop.getProperty("userId"));
        System.out.println(res.asString());
    }
}
