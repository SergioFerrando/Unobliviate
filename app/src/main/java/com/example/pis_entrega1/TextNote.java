package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

public class TextNote extends AppCompatActivity implements View.OnClickListener{
    Text text = new Text();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("entra en la nota de texto");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
        findViewById(R.id.TextSaveButton).setOnClickListener(this);
        findViewById(R.id.TextCheckList).setOnClickListener(this);
        findViewById(R.id.TextRememberButton).setOnClickListener(this);
        findViewById(R.id.TextShareButton).setOnClickListener(this);
        findViewById(R.id.TextDeleteButton).setOnClickListener(this);
        String titleTemp = this.findViewById(R.id.editTextTitleTextNote).toString();
        if (titleTemp != null){
            text.setName(titleTemp);
        }else{
            text.setName("");
        }
        String textTemp = this.findViewById(R.id.editTextTextNote).toString();
        if (textTemp != null){
            text.setText(textTemp);
        }else{
            text.setText("");
        }
    }

    public void goToShareIntent(){
        /*Intent intent = new Intent(Intent.ACTION_SEND);
        String shareBody = ContactsContract.CommonDataKinds.Note.getText().toString();
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Text Note");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent,"Share using... "));*/
    }

    public void goToRememberIntent(){
        DatePickerFragment calendar = new DatePickerFragment();
        calendar.show(getSupportFragmentManager(),"DatePicker");
        TimePickerFragment hour = new TimePickerFragment();
        hour.show(getSupportFragmentManager(),"HOUR");
    }

    public void goToMainIntent(){
        Intent n = new Intent(this, MainActivity.class);
        startActivity(n);
    }

    public void CheckList(){
        text.setText(this.findViewById(R.id.editTextTextNote).toString());
        String temp = text.getText() + "\n -";
        text.setText(temp);
    }

    @Override
    public void onClick(View v) {
        if (R.id.TextSaveButton == v.getId()){
            String titleTemp = this.findViewById(R.id.editTextTitleTextNote).toString();
            if (titleTemp != null){
                this.text.setName(titleTemp);
            }else{
                this.text.setName("");
            }
            String textTemp  = this.findViewById(R.id.editTextTextNote).toString();
            if (textTemp != null){
                this.text.setText(textTemp);
            }else{
                this.text.setText("");
            }
            long millis = System.currentTimeMillis();
            Text text_temp = new Text(millis, this.text.getName(), this.text.getText());
            //nc.addTextNote(this.text.getName(), this.text.getText());
            Intent intent = new Intent();
            intent.putExtra("title", this.text.getName());
            intent.putExtra("text", this.text.getText());
            intent.putExtra("date", System.currentTimeMillis());
            setResult(RESULT_OK, intent);
            finish();
        }
        if (R.id.TextCheckList == v.getId()){
            CheckList();
        }
        if (R.id.TextRememberButton == v.getId()){
            goToRememberIntent();
        }
        if (R.id.TextShareButton == v.getId()){
            goToShareIntent();
        }
        if (R.id.TextDeleteButton == v.getId()){
            goToMainIntent();
        }

    }
}