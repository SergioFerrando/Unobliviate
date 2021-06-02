package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Activity to show the text notes
 */
public class TextNote extends AppCompatActivity implements View.OnClickListener{

    Text text = new Text();
    EditText title, content;
    int position = -1;
    boolean existente;

    /**
     * OnCreate method to initialize the layout components and get the data of the note if it has
     * already been created and you want to open it.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
        findViewById(R.id.TextSaveButton).setOnClickListener(this);
        findViewById(R.id.TextCheckList).setOnClickListener(this);
        findViewById(R.id.TextShareButton).setOnClickListener(this);
        findViewById(R.id.TextDeleteButton).setOnClickListener(this);
        title = this.findViewById(R.id.editTextTitleTextNote);
        content = this.findViewById(R.id.editTextTextNote);
        if (getIntent().getStringExtra("newTitleText") != null){
            this.text.setName(getIntent().getStringExtra("newTitleText"));
            this.text.setText(getIntent().getStringExtra("newTextText"));
            this.text.setID(getIntent().getStringExtra("id"));
            this.position = getIntent().getIntExtra("positionText", 0);
            title.setText(this.text.getName());
            content.setText(this.text.getText());
            this.existente = true;
        }else{
            title = this.findViewById(R.id.editTextTitleTextNote);
            if (title != null){
                text.setName(title.getText().toString());
            }else{
                text.setName("");
            }
            content = this.findViewById(R.id.editTextTextNote);
            if (content.getText() != null){
                text.setText(content.getText().toString());
            }else{
                text.setText("");
            }
        }
    }

    /**
     * Method to open the share mode, in which one the user can share the note. The necessary data
     * is sent to the activity through the Intent used to open it.
     */
    public void goToShareIntent(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content.getText().toString());
        intent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(intent,text.getName());
        startActivity(shareIntent);
    }

    /**
     * Method to go back to the Main activity, using an Intent to send the necessary data and
     * establishing it as the result of the current activity.
     */
    public void goToMainIntent(){
        Intent intent = new Intent();
        intent.putExtra("positionDelete", this.position);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    /**
     * Method to add a new "check" item to the text note.
     */
    public void CheckList(){
        String temp = (content.getText() + "\n -");
        content.setText(temp);
    }

    /**
     * OnClick method to add action to each button inside the activity.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (R.id.TextSaveButton == v.getId()){
            if (title.getText().toString() != null) {
                this.text.setName(title.getText().toString());
            } else {
                this.text.setName("");
            }
            if (content.getText().toString() != null) {
                this.text.setText(content.getText().toString());
            } else {
                this.text.setText("");
            }
            if(this.text.getID() == null) {
                Intent intent = new Intent();
                intent.putExtra("title", this.text.getName());
                intent.putExtra("text", this.text.getText());
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.FRANCE);
                String date = df.format(Calendar.getInstance().getTime());
                intent.putExtra("date", date);
                intent.putExtra("positionText", this.position);
                setResult(RESULT_OK, intent);
                finish();
            }else{
                Intent intent = new Intent();
                intent.putExtra("title", this.text.getName());
                intent.putExtra("text", this.text.getText());
                intent.putExtra("id", this.text.getID());
                intent.putExtra("positionText", this.position);
                setResult(5, intent);
                finish();
            }
        }
        if (R.id.TextCheckList == v.getId()){
            CheckList();
        }
        if (R.id.TextShareButton == v.getId()){
            goToShareIntent();
        }
        if (R.id.TextDeleteButton == v.getId()){
            goToMainIntent();
        }

    }
}