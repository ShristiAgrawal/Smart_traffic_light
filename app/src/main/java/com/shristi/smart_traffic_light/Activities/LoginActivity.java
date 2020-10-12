package com.shristi.smart_traffic_light.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shristi.smart_traffic_light.Api.RetrofitClient;
import com.shristi.smart_traffic_light.Api.RetrofitService;
import com.shristi.smart_traffic_light.Models.Api_response;
import com.shristi.smart_traffic_light.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText id,password;
    private RetrofitService service;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id=findViewById(R.id.id);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    private void login() {

        service = RetrofitClient.getinstance().create(RetrofitService.class);

        Call<Api_response> call = service.login(id.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<Api_response>() {
            @Override
            public void onResponse(Call<Api_response> call, Response<Api_response> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Successfully logged in",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,MapActivity.class);
                    startActivity(intent);

                }
                else {
                    if(response.code()==400)
                        Toast.makeText(getApplicationContext(),"Id or password empty",Toast.LENGTH_SHORT).show();
                    else if(response.code()==401)
                        Toast.makeText(getApplicationContext(),"Credentials did not match",Toast.LENGTH_SHORT).show();
                    else if(response.code()==404)
                        Toast.makeText(getApplicationContext(),"User not found",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Api_response> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error logging in", Toast.LENGTH_LONG).show();
            }

        });
    }

}
