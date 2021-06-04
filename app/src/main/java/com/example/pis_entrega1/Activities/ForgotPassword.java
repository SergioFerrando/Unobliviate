package com.example.pis_entrega1.Activities;

import android.content.Intent;
import android.os.Bundle;
import com.example.pis_entrega1.*;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pis_entrega1.R;

/**
 * Class that contains view From Forgot Password
 */
public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{
    EditText email;

    /**
     * Set all contents of the view and the on click listeners
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_forgot_password);
        findViewById(R.id.CancelButton).setOnClickListener(this);
        findViewById(R.id.ConfirmButton).setOnClickListener(this);
        email = findViewById(R.id.TextEmail);
    }

    /**
     * Method that captures all the on click listeners
     * if we press cancel button we go to auth activity without changes
     * if we press confirm button and email text view isn't empty we pass the email to Auth activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.CancelButton){
            Intent n = new Intent();
            setResult(RESULT_CANCELED,n);
            finish();
        }if(v.getId() == R.id.ConfirmButton){
            if(email.getText().length() == 0){
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