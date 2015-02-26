package multiplayer;

import java.io.IOException;

import java.io.OutputStream;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.FileInputStream;

import java.util.Properties;

public class Config {
	
	Properties config = new Properties();
	
	OutputStream configFileO;
	InputStream configFileI;
	
	void set(String property, String data) throws IOException {
		
		configFileO = new FileOutputStream("dawnoftheimmortals.properties");
			
		config.setProperty(property, data);
			
		config.store(configFileO, null);
		
	}
	
	String load(String property) throws IOException {
		
		configFileI = new FileInputStream("dawnoftheimmortals.properties");
		
		config.load(configFileI);
		
		return config.getProperty(property);
		
	}

}
