package com.example.dmswj.management;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Button btn_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");

                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name",name);
                                intent.putExtra("username", username);
                                intent.putExtra("age",age);

                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패했습니다.")
                                        .setPositiveButton("다시 시도하십시오", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }


    public void login(View v) {
        HashMap map = new HashMap<>();
        map.keySet();

        Thread th = new Thread((new Runnable() {
            @Override
            public void run() {
                int Num = 1;
                while (true) {
                    Log.i("Thread", "" + Num);
                    Num++;

                    try {
                        String page = Common.SERVER_URL + "/dashboard/login_check.php";
                        HttpClient http = new DefaultHttpClient();
                        ArrayList<NameValuePair> postData = new ArrayList<>();
                        postData.add(
                                new BasicNameValuePair("userid", editId.getText().toString())
                        );
                        postData.add(new BasicNameValuePair("passwd", aditPwd.getText().toString));

                        UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");

                        HttpPost httpPost = new HttpPost(page);
                        httpPost.setEntity(request);
                        HttpResponse response = http.execute(httpPost);

                        String body = EntityUtils.toString(response.getentity());
                        JSONObject jsonObj = new JSONObject(body);
                        result = jsonObj.getString("message");

                        runOnUiThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }));

        th.start();


    }
}

