package com.example.hansmartin.finalproject2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    String userID;
    EditText tName;
    EditText tEmail;
    EditText pPassword;
    EditText tPhone;
    EditText tSMS;
    Button bSMS;
    Button bLogin;
    Button bRegister;
    SmsManager smsM;
    String smsCode = "";

    Database db = new Database(this);

    public void generateID(){

        Cursor cUserData = db.getUserData();

        if(cUserData.getCount()==0){
            userID="US001";
        }else if (cUserData.getCount()!=0){
            while(cUserData.moveToNext()){

                String lastID = cUserData.getString(0);
                int id = Integer.parseInt(lastID.substring(lastID.indexOf("S")+1,lastID.length()));
                if(id < 9){
                    userID="US00"+(id+1);
                }else if(id < 99){
                    userID="US0"+(id+1);
                }else if (id < 999){
                    userID="US"+(id+1);
                }

                //final delete
                try {
                    Log.i("ID",cUserData.getString(0));
                    Log.i("name",cUserData.getString(1));
                    Log.i("email",cUserData.getString(2));
                    Log.i("pass",cUserData.getString(3));
                    Log.i("phone",cUserData.getString(4));
                }catch (Exception e){

                }
                //final delete
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tName = findViewById(R.id.tName);
        tEmail = findViewById(R.id.tEmail);
        pPassword = findViewById(R.id.pPassword);
        tPhone = findViewById(R.id.tPhone);
        tSMS = findViewById(R.id.tSMS);
        bSMS = findViewById(R.id.bSMS);
        bLogin = findViewById(R.id.bLogin);
        bRegister = findViewById(R.id.bRegister);

        smsM = SmsManager.getDefault();

        if (checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},0);
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        }

        bSMS.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);

        Random r = new Random();

        for (int i=0; smsCode.length()<5; i++ ){
            char cX = (char) (r.nextInt(95)+32);
            if (Character.isLetterOrDigit(cX)){
                smsCode = smsCode+cX;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        String name = tName.getText().toString();
        String email = tEmail.getText().toString();
        String password = pPassword.getText().toString();
        String phone = tPhone.getText().toString();
        String sms = tSMS.getText().toString();
        int cekAt = 0;
        int cekDot = 0;
        int cekHuruf = 0;
        int cekAngka = 0;
        int cekEmail = 0;
        int cekSymbol = 0;
        int indexDot = email.indexOf('.');
        int indexComa = email.indexOf(',');
        int indexAt = email.indexOf('@');
        int indexUnderscore = email.indexOf('_');
        int lastIndexDot = email.lastIndexOf('.');
        int lastIndexComa = email.lastIndexOf(',');
        int lastIndexAt = email.lastIndexOf('@');
        int lastIndexUnderscore = email.lastIndexOf('_');
        int cekUppercase = 0;
        int cekLowerCase = 0;
        int cekPasswordSymbol = 0;
        int cekPhone = 1;
        int emailTaken = 0;

        Cursor cUserData = db.getUserData();

        if (cUserData.getCount()!=0){
            while(cUserData.moveToNext()){
                String dbEmail = cUserData.getString(2);

                if (dbEmail.equals(email)){
                    emailTaken++;
                }
            }
        }

        for(int i = 0; i<email.length(); i++){

            char cEmail = email.charAt(i);

            if(cEmail==64){
                cekAt++;
            }
            if(cEmail==46){
                cekDot++;
            }
            if(Character.isLetter(cEmail)) {
                cekHuruf++;
            }
            if(Character.isDigit(cEmail)) {
                cekAngka++;
            }
            if(cekHuruf!=0&&cekAngka!=0){
                cekEmail++;
            }
            if(!email.contains(".")&&!email.contains(",")&&!email.contains("@")&&!email.contains("_")){
                cekEmail=0;
            }
        }

        if(indexDot-lastIndexDot==1||indexDot-lastIndexDot==-1||indexDot-indexComa==1||indexDot-indexComa==-1||indexDot-indexAt==1||indexDot-indexAt==-1||indexDot-indexUnderscore==1||indexDot-indexUnderscore==-1||
                indexComa-lastIndexComa==1||indexComa-lastIndexComa==-1||indexComa-indexAt==1||indexComa-indexAt==-1||indexComa-indexUnderscore==1||indexComa-indexUnderscore==-1||
                indexAt-lastIndexAt==1||indexAt-lastIndexAt==-1||indexAt-indexUnderscore==1||indexAt-indexUnderscore==-1||
                indexUnderscore-lastIndexUnderscore==1||indexUnderscore-lastIndexUnderscore==-1||
                indexDot-lastIndexComa==1||indexDot-lastIndexComa==-1||indexDot-lastIndexAt==1||indexDot-lastIndexAt==-1||indexDot-lastIndexUnderscore==1||indexDot-lastIndexUnderscore==-1||
                indexComa-lastIndexDot==1||indexComa-lastIndexDot==-1||indexComa-lastIndexAt==1||indexComa-lastIndexAt==-1||indexComa-lastIndexUnderscore==1||indexComa-lastIndexUnderscore==-1||
                indexAt-lastIndexDot==1||indexAt-lastIndexDot==-1||indexAt-lastIndexComa==1||indexAt-lastIndexComa==-1||indexAt-lastIndexUnderscore==1||indexAt-lastIndexUnderscore==-1||
                indexUnderscore-lastIndexDot==1||indexUnderscore-lastIndexDot==-1||indexUnderscore-lastIndexComa==1||indexUnderscore-lastIndexComa==-1||indexUnderscore-lastIndexAt==1||indexUnderscore-lastIndexAt==-1){
            cekSymbol++;
        }

        for(int i = 0; i<password.length(); i++){

            char cPassword = password.charAt(i);

            if(Character.isUpperCase(cPassword)){
                cekUppercase++;
            }
            if(Character.isLowerCase(cPassword)){
                cekLowerCase++;
            }
            if(!Character.isLetterOrDigit(cPassword)){
                cekPasswordSymbol++;
            }
        }

        if(phone.startsWith("+62")){

            for (int i = 1; i < phone.length()-1; i++) {

                char cPhone = phone.charAt(i);

                if(!Character.isDigit(cPhone)){
                    cekPhone=0;
                }
            }
        }else{

            for (int i = 0; i < phone.length(); i++) {

                char cPhone = phone.charAt(i);

                if(!Character.isDigit(cPhone)){
                    cekPhone=0;
                }
            }
        }

        if (v == bSMS){

            if (phone.length() < 10 || phone.length() > 14){
                Toast.makeText(this, "Phone Number must be valid", Toast.LENGTH_SHORT).show();
            }else if(!phone.startsWith("0") && !phone.startsWith("+62")){
                Toast.makeText(this, "Phone Number must be valid", Toast.LENGTH_SHORT).show();
            }else if(cekPhone==0){
                Toast.makeText(this, "Phone Number must be valid", Toast.LENGTH_SHORT).show();
            }else{
                smsM.sendTextMessage(phone, null, smsCode, null, null);
                //final delete smsCode
                Toast.makeText(this, smsCode+" "+"SMS Code Succesfully sent", Toast.LENGTH_SHORT).show();
                //final delete smsCode
            }

        }else if (v == bRegister){

            if(!name.contains(" ") || name.startsWith(" ")||name.endsWith(" ")){
                Toast.makeText(this, "Name must be 2 words", Toast.LENGTH_SHORT).show();
            }else if(cekAt != 1 || cekDot < 1){
                Toast.makeText(this, "E-Mail must have one ‘@’ and at least one ‘.’", Toast.LENGTH_SHORT).show();
            }else if(cekEmail==0){
                Toast.makeText(this, "E-Mail must be in alphanumeric format and may contain ‘.’, ‘@’ and ‘_’ symbol", Toast.LENGTH_SHORT).show();
            }else if(cekSymbol==1){
                Toast.makeText(this, "E-Mail symbol cannot be placed beside each other", Toast.LENGTH_SHORT).show();
            }else if(cekUppercase==0||cekLowerCase==0||cekPasswordSymbol==0){
                Toast.makeText(this, "Password must contain one uppercase letter, one lowercase letter, and one symbol.", Toast.LENGTH_SHORT).show();
            }else if(phone.length() < 10 || phone.length() > 14){
                Toast.makeText(this, "Phone Number length must be between 10 and 14 digits", Toast.LENGTH_SHORT).show();
            }else if(!phone.startsWith("0") && !phone.startsWith("+62")){
                Toast.makeText(this, "Phone Number must starts with ‘0’ or ‘+62’", Toast.LENGTH_SHORT).show();
            }else if(cekPhone==0){
                Toast.makeText(this, "Phone Number must contains only digits, except ‘+’ for phone number that is written in country code format (‘+62’)", Toast.LENGTH_SHORT).show();
//            }else if (cekSentSMS==0){
//                Toast.makeText(this, "Must sent SMS Code request first", Toast.LENGTH_SHORT).show();
            }else if (!sms.equals(smsCode)){
                Toast.makeText(this, "SMS Code must be the same with the confirmation code", Toast.LENGTH_SHORT).show();
            }else if (emailTaken==1){
                Toast.makeText(this, "E-mail already taken", Toast.LENGTH_SHORT).show();
            }else{

                generateID();
                db.insertUser(userID,name,email,password,phone);

                //final delete
                if(cUserData.getCount()==0){

                }else if (cUserData.getCount()!=0){
                    while(cUserData.moveToNext()){
                        try {
                            Log.i("ID",cUserData.getString(0));
                            Log.i("name",cUserData.getString(1));
                            Log.i("email",cUserData.getString(2));
                            Log.i("pass",cUserData.getString(3));
                            Log.i("phone",cUserData.getString(4));
                        }catch (Exception e){

                        }
                    }
                }
                //final delete

                Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show();
            }
        }else if(v==bLogin){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
