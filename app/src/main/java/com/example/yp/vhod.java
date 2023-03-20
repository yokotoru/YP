package com.example.yp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class vhod extends AppCompatActivity {

    final static String Email = "Email";
    final static String Password = "Password";
    EditText email;
    EditText password;
    SharedPreferences preferences;
    public static Mask users;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhod);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        getInfo();
    }
    public void Profile(View v){
        if (email.getText().toString().equals("")||password.getText().toString().equals("")){
            Toast.makeText(vhod.this,"Обязательные поля не заполнены", Toast.LENGTH_SHORT).show();
        }
        else{
            Pattern pattern = Pattern.compile("@",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email.getText().toString());
            boolean bool = matcher.find();
            if (bool){
                SingIN();
            }
            else{
                Toast.makeText(vhod.this, "Проверьте поле email", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void SingIN(){
        String emailstr = String.valueOf(email.getText());
        String passwordstr = String.valueOf(password.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitapi = retrofit.create(RetrofitAPI.class);

        UserModel modelProf = new UserModel(emailstr, passwordstr);
        Call<Mask> call = retrofitapi.createUser(modelProf);
        call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(Call<Mask> call, Response<Mask> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(vhod.this, "Пользователя с такой почтой нет", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body() != null) {
                    if (response.body().getToken()!=null){
                        saveInfo();
                        users = response.body();
                        Intent intent = new Intent(vhod.this, GlavStr.class);
                        Bundle b = new Bundle();
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }


            @Override
            public void onFailure(Call<Mask> call, Throwable t) {
                Toast.makeText(vhod.this,"Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void saveInfo(){
        preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Email, email.getText().toString());
        editor.putString(Password,password.getText().toString());
        editor.commit();
    }

    public void getInfo(){
        preferences = getPreferences(MODE_PRIVATE);
        String emailu = preferences.getString(Email,"");
        String passwordu = preferences.getString(Password,"");
        email.setText(emailu);
        password.setText(passwordu);
    }

    public void Registr(View v) {
        Intent intent = new Intent(vhod.this, Regist.class);
        startActivity(intent);
    }
}