package utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Utils {
    public static void setEnv(String key,String value) throws ConfigurationException {
        PropertiesConfiguration config=new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }
    public static int generateRandomNumber(int max,int min){
        double randomId=Math.random()*(max-min)+min;
        return (int) randomId;
    }
}
