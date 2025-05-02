package com.example.fitplus;


import java.util.HashMap;
import java.util.Map;

public class ApplicationUtil {

    public static Map getResponseMap(int code, String message) throws Exception
    {
        Map<String, String> response = new HashMap<>();
        response.put("code", String.valueOf(code));
        response.put("message", message);

        return response;
    }
}
