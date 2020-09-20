package com.fitapp.logic.googlemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleMapConnection implements AutoCloseable {
    private final Logger logger = Logger.getLogger(getClass().getName());


    public StringBuilder getResponse(String newUrl) throws IOException {
        URL url = new URL(newUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        StringBuilder responseContent = new StringBuilder();
        //Request setup
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int status = connection.getResponseCode();
        BufferedReader reader;
        String line;

        if (status > 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }

        return responseContent;
    }


    @Override
    public void close() throws Exception {
        logger.log(Level.INFO, "connection closed");
    }

}
