package com.example.dmswj.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmswj on 2018-03-14.
 */

public class RegisterRequest extends StringRequest {

    final static private String REGISTER_REQUEST_URL = "http://127.0.0.1/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String name, String username, int age, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener,null);
        parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("age", age + "");
    }

    public  Map<String, String> getParameters(){
        return parameters;
    }
}
