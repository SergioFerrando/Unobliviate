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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class photo_taken extends AppCompatActivity implements View.OnClickListener{

    Photo p = new Photo();
    ImageView miniatura;
    EditText title;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_taken);
        findViewById(R.id.PhotoDeleteButton).setOnClickListener(this);
        findViewById(R.id.PhotoRememberButton).setOnClickListener(this);
        findViewById(R.id.PhotoSaveButton).setOnClickListener(this);
        findViewById(R.id.PhotoShareButton).setOnClickListener(this);
        miniatura = findViewById(R.id.Foto);
        title = findViewById(R.id.editPhotoTitleNote);
        goFromPhotoNote();
    }

    public void goFromPhotoNote(){
        byte[] byteArray = ((byte[]) getIntent().getExtras().get("photo"));
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        miniatura.post(new Runnable() {
            @Override
            public void run() {
                miniatura.setImageBitmap(Bitmap.createScaledBitmap(bmp, miniatura.getWidth(), miniatura.getHeight(), false));
            }
        });
        //p.setPhotoTitle(title.getText().toString());
    }

    public void goToMainIntentNoSave(){
        Intent n = new Intent(this,MainActivity.class);
        startActivity(n);
    }

    public void goToMainIntentSave(){
        p.setMiniatura(miniatura);
        p.setName(title.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("title_photo", this.p.getName());
        intent.putExtra("date_audio", System.currentTimeMillis());

        Bitmap bitmap = ((BitmapDrawable)miniatura.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,90, stream);
        byte[] image = stream.toByteArray();

        intent.putExtra("photo", image);
        setResult(RESULT_OK, intent);
        finish();
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