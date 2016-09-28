package com.example.aaron.greendaoexample;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.aaron.greendaoexample.db.PhoneList;
import com.example.aaron.greendaoexample.db.User;

public class AddUserActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtEdad;
    EditText txtPhone;


    boolean flagIsUpdate = false;
    Long usrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        txtName = ((EditText) findViewById(R.id.userName));
        txtEdad = ((EditText) findViewById(R.id.userAge));
        txtPhone = ((EditText) findViewById(R.id.userPhone));

        Intent i = getIntent();
        flagIsUpdate = i.getBooleanExtra("isUpdate", false);

        if(flagIsUpdate){
            txtName.setText(i.getStringExtra("nameUser"));
            txtEdad.setText(i.getIntExtra("ageUser",0) + "");
            txtPhone.setText(i.getStringExtra("phoneUser"));
            usrId = i.getLongExtra("idUser", 0);
        }


    }


    public void add(View view) {

        Intent i = new Intent();
        i.putExtra("nameUser", txtName.getText().toString());
        i.putExtra("ageUser",  txtEdad.getText().toString());
        i.putExtra("phoneUser",  txtPhone.getText().toString());
        i.putExtra("idUser",  usrId);
        setResult(RESULT_OK, i);
        finish();
    }
}
