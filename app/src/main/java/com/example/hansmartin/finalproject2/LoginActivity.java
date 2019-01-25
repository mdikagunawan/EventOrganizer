package com.example.hansmartin.finalproject2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    RegisterActivity registerActivity;

    EditText tEmail;
    EditText pPassword;
    Button bLogin;
    Button bRegister;

    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tEmail = findViewById(R.id.tEmail);
        pPassword = findViewById(R.id.pPassword);
        bLogin = findViewById(R.id.bLogin);
        bRegister = findViewById(R.id.bRegister);

        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        registerActivity = new RegisterActivity();

        Intent intent=null;

        if (v==bLogin){
            String email = tEmail.getText().toString();
            String password = pPassword.getText().toString();
            int validAcc = 0;
            String dbUserID;

            Cursor cUserData = db.getUserData();
            Cursor cValidateLogin = db.validateLogin(email);
            Cursor cGetUserID = db.getUserID(email);

            if(cUserData.getCount()==0){
                validAcc = 0;
            }else if (cUserData.getCount()!=0){
                while(cUserData.moveToNext()){
                    String dbEmail = cUserData.getString(2);

                    if (dbEmail.equals(email)){
                        if (cValidateLogin.getCount()==0){
                            validAcc=0;

                        }else if (cValidateLogin.getCount()!=0){
                            while (cValidateLogin.moveToNext()){
                                String dbPassword = cValidateLogin.getString(0);

                                if (dbPassword.equals(password)){
                                    validAcc++;
                                }else{
                                    validAcc=0;
                                }
                            }
                        }
                    }
                }
            }

            if(email.isEmpty()){
                Toast.makeText(this, "Email must be filled", Toast.LENGTH_SHORT).show();
            }else if(password.isEmpty()){
                Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
            }else if(validAcc==0){
                Toast.makeText(this, "Email or Password is invalid", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MainActivity.class);

                while(cGetUserID.moveToNext()){
                    dbUserID = cGetUserID.getString(0);
                    intent.putExtra("userID", dbUserID);
                    startActivity(intent);
                }
            }
        }else if(v == bRegister){
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
