package com.example.pis_entrega1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{
    EditText email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_forgot_password);
        findViewById(R.id.CancelButton).setOnClickListener(this);
        findViewById(R.id.ConfirmButton).setOnClickListener(this);
        email = findViewById(R.id.TextEmail);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.CancelButton){
            Intent n = new Intent();
            setResult(RESULT_CANCELED,n);
            finish();
        }if(v.getId() == R.id.ConfirmButton){
            if(email.getTextSize() == 0){
                email.setText("Introduce un email para confirmar");
            }else{
                Intent n = new Intent();
                n.putExtra("email", email.getText().toString());
                setResult(RESULT_OK, n);
                finish();
            }
        }
    }
}