package com.example.pis_entrega1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PhotoTaken extends AppCompatActivity implements View.OnClickListener{

    Photo p = new Photo();
    ImageView miniatura;
    TextView title;
    int position = -1;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_taken);
        findViewById(R.id.PhotoDeleteButton).setOnClickListener(this);
        findViewById(R.id.PhotoRememberButton).setOnClickListener(this);
        findViewById(R.id.PhotoSaveButton).setOnClickListener(this);
        findViewById(R.id.PhotoShareButton).setOnClickListener(this);
        miniatura = findViewById(R.id.Foto);
        title = findViewById(R.id.PhotoTitleNote);
        goFromPhotoNote();
    }

    public void goFromPhotoNote(){
        if (getIntent().getStringExtra("newTitlePhoto") != null){
            p.setName(getIntent().getStringExtra("newTitlePhoto"));
            title.setText(p.getName());
            p.setAddress(getIntent().getStringExtra("path"));
            setPic();
            p.setId(getIntent().getStringExtra("id"));
            this.position = getIntent().getIntExtra("positionPhoto", 0);
        }else {
            path = getIntent().getStringExtra("path");
            p.setAddress(path);
            p.setName(getIntent().getStringExtra("titlePhoto"));
            title.setText(p.getName());
            setPic();
        }
    }

    private void setPic() {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(p.getAddress(), bmOptions);
        miniatura.setImageBitmap(bitmap);
    }

    public void goToMainIntentNoSave(){
        Intent intent = new Intent();
        intent.putExtra("positionDelete", this.position);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void goToMainIntentSave(){
        if(p.getId() == null) {
            p.setName(title.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("title_photo", this.p.getName());
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.FRANCE);
            String date = df.format(Calendar.getInstance().getTime());
            intent.putExtra("date_photo", date);
            intent.putExtra("path", p.getAddress());
            setResult(RESULT_OK, intent);
            finish();
        }else{
            p.setName(title.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("title_photo", this.p.getName());
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.FRANCE);
            String date = df.format(Calendar.getInstance().getTime());
            intent.putExtra("id", p.getId());
            intent.putExtra("path", p.getAddress());
            intent.putExtra("positionPhoto", this.position);
            setResult(3, intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.PhotoDeleteButton){
            goToMainIntentNoSave();
        }if(v.getId() == R.id.PhotoSaveButton){
            goToMainIntentSave();
        }if(v.getId() == R.id.PhotoShareButton){
            goToShareIntent();
        }if(v.getId() == R.id.PhotoRememberButton){

        }
    }

    private void goToShareIntent() {
        Drawable drawable=miniatura.getDrawable();
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
        try {
            File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + title.getText().toString() + ".jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID +".provider", file);

            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");

            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}