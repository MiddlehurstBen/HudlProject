package com.asos.ip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {

    public static Map<String, String> getLoginDetailsAsMap() {
        Map<String, String> loginDetails = new HashMap<>();

        Properties properties = new Properties();

        String loginDetailsConfigFile = "loginDetails.config";

        try {
            InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(loginDetailsConfigFile);

            if (inputStream == null) {
                File file = new File(loginDetailsConfigFile);
                if (file.exists()) {
                    inputStream = new FileInputStream(file);
                } else {
                    throw new FileNotFoundException("Resource not found: " + loginDetailsConfigFile);
                }
            }
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        }

        loginDetails.put("email", properties.getProperty("login"));
        loginDetails.put("password", properties.getProperty("password"));

        return loginDetails;
    }
}
