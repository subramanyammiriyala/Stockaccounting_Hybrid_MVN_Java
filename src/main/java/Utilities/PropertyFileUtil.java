package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
	public static String getValueForKey(String Key) throws Exception{
		Properties configProperties=new Properties();
		FileInputStream fis=new FileInputStream("E:\\subramanyam\\Stockaccounting_Hybrid_MVN\\PropertiesFile\\Enviroment.properties");
		configProperties.load(fis);
		return configProperties.getProperty(Key);
		
	}

}
