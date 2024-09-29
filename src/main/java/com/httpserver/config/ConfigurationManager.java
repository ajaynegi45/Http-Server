package com.httpserver.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.httpserver.utils.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager configurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }


//    User to load a configuration file by the path provided
    public void loadConfigurationFile(String filePath)  {
        FileReader fileReader = null;
        try{
            fileReader = new FileReader(filePath);
        }catch (FileNotFoundException e){
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
//        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int i;
        try {
            while( (i = fileReader.read()) != -1 ){
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode config = null;
        try {
            config = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration file",e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(config,Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration file INTERNAL",e);
        }

    }


    // Returns the current loaded Configuration
    public Configuration getCurrentConfiguration() {
        if ( myCurrentConfiguration == null ) {
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}
