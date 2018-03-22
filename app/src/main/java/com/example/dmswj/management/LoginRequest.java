package com.example.dmswj.management;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmswj on 2018-03-23.
 */

public class LoginRequest extends StringRequest{
    final static private String LOGIN_REQUEST_URL = "http://127.0.0.1/Login.php";
    private Map<String, String> parameters;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener,null);
        parameters = new HashMap<>();
        parameters.put("username", username);
        parameters.put("password", password);
    }

    public  Map<String, String> getParameters(){
        return parameters;
    }
}
