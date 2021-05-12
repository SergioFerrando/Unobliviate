package com.example.pis_entrega1;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;

        import java.io.Serializable;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email, Password;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        email = findViewById(R.id.EmailAdress);
        Password = findViewById(R.id.Password);
        error = findViewById(R.id.ErrorMessaje);
        findViewById(R.id.buttonAcceder).setOnClickListener(this);
        findViewById(R.id.buttonRegistrer).setOnClickListener(this);
    }

    public void goToMainIntent(){
        Intent n = new Intent(this,MainActivity.class);
        startActivity(n);
    }
    public void ErrorLogin(String Error){
        error.setText(Error);
    }

    public void register(){
        if(email.getTextSize() != 0 && Password.getTextSize() != 0){
            DatabaseAdapter da = new DatabaseAdapter(email.getText().toString(),Password.getText().toString(),"register",this);
        }
    }

    public void logIn(){
        if(email.getTextSize() != 0 && Password.getTextSize() != 0){
            DatabaseAdapter da= new DatabaseAdapter(email.getText().toString(),Password.getText().toString(),"logIn",this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonRegistrer){
            register();
        }if(v.getId() == R.id.buttonAcceder){
            logIn();
        }
    }
}