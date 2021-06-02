package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    EditText email, Password;
    TextView error;
    CheckBox checkBox;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);
        email = findViewById(R.id.EmailAdress);
        Password = findViewById(R.id.Password);
        error = findViewById(R.id.ErrorMessaje);
        checkBox = findViewById(R.id.checkBox);

        findViewById(R.id.ForgotPassword).setOnClickListener(this);
        findViewById(R.id.buttonAcceder).setOnClickListener(this);
        findViewById(R.id.buttonRegistrer).setOnClickListener(this);

        preferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        editor = preferences.edit();

        loadPreferences();
    }

    private void loadPreferences() {
        String checkbox = preferences.getString("checkbox_clicked", "");

        if (checkbox.equals("true")) {
            String userName = preferences.getString("saved_username", "");
            String password = preferences.getString("saved_password", "");

            email.setText(userName);
            Password.setText(password);

            logIn();
        } else {
            email.setText("");
            Password.setText("");
        }
    }

    public void goToMainIntent(){
        Intent n = new Intent(this,MainActivity.class);
        startActivity(n);
    }

    public void ErrorLogin(String Error){
        error.setText(Error);
    }

    public void register(){
        if(email.getText().length() != 0 && Password.getText().length() != 0){
            DatabaseAdapter da = new DatabaseAdapter(email.getText().toString(),Password.getText().toString(),"register",this);
        }
    }

    public void logIn(){
        if(email.getText().length() != 0 && Password.getText().length() != 0){

            DatabaseAdapter da = new DatabaseAdapter(email.getText().toString(),Password.getText().toString(),"logIn",this);

            email.setText("");
            Password.setText("");

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                DatabaseAdapter da = new DatabaseAdapter();
                da.ForgotPassword(intent.getStringExtra("email"));
                error.setText("Forgoten email sent successfully");
            }if(resultCode == RESULT_CANCELED){

            }
        }
    }

    public void goToForgetPassword(){
        Intent n1 = new Intent(this, ForgotPassword.class);
        startActivityForResult(n1,1);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonRegistrer){
            register();
        }if(v.getId() == R.id.buttonAcceder){
            if (checkBox.isChecked()) {
                editor.putString("checkbox_clicked", "true");
                editor.putString("saved_username", email.getText().toString());
                editor.putString("saved_password", Password.getText().toString());

                editor.commit();
                Toast.makeText(AuthActivity.this, "Checked", Toast.LENGTH_SHORT).show();
            } else {
                editor.putString("checkbox_clicked", "false");
                editor.putString("saved_username", "");
                editor.putString("saved_password", "");

                editor.commit();
            }
            logIn();
        }if(v.getId() == R.id.ForgotPassword){
            goToForgetPassword();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("checkbox")) {
            Toast.makeText(AuthActivity.this, "se intenta", Toast.LENGTH_SHORT).show();

            if (sharedPreferences.getString("checkbox_clicked", "").equals("false")) {
                checkBox.setChecked(false);
                email.setText("");
                Password.setText("");
            }
        }
    }
}