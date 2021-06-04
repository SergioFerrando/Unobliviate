package com.example.pis_entrega1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import com.example.pis_entrega1.*;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pis_entrega1.Note.Photo;
import com.example.pis_entrega1.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Activity to show a photo taken and interact with it.
 */
public class PhotoTaken extends AppCompatActivity implements View.OnClickListener {

    Photo p = new Photo();
    ImageView miniatura;
    TextView title;
    int position = -1;
    String path;

    /**
     * onCreate method to set an "OnClickListener" to the buttons in the layout "activity_photo_taken",
     * set the items miniatura and title and call the method "goFromPhotoNote".
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_taken);
        findViewById(R.id.PhotoDeleteButton).setOnClickListener(this);
        findViewById(R.id.PhotoSaveButton).setOnClickListener(this);
        findViewById(R.id.PhotoShareButton).setOnClickListener(this);
        miniatura = findViewById(R.id.Foto);
        title = findViewById(R.id.PhotoTitleNote);
        goFromPhotoNote();
    }

    /**
     * Method to check if the note has already been created and get the image and title of it.
     */
    public void goFromPhotoNote() {
        if (getIntent().getStringExtra("newTitlePhoto") != null) {
            p.setName(getIntent().getStringExtra("newTitlePhoto"));
            title.setText(p.getName());
            p.setAddress(getIntent().getStringExtra("path"));
            setPic();
            p.setID(getIntent().getStringExtra("id"));
            p.setUrl(getIntent().getStringExtra("url"));
            this.position = getIntent().getIntExtra("positionPhoto", 0);
        } else {
            path = getIntent().getStringExtra("path");
            p.setAddress(path);
            p.setName(getIntent().getStringExtra("titlePhoto"));
            title.setText(p.getName());
            setPic();
        }
    }

    /**
     * Method to set the image of the note to the attribute "miniatura" to can see it.
     */
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

    /**
     * Method to go back to main intent without save the note.
     */
    public void goToMainIntentNoSave() {
        Intent intent = new Intent();
        intent.putExtra("positionDelete", this.position);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    /**
     * Method to go back to main intent saving the note, checking if the note is new or if it has
     * already been created, to overwrite it in the database.
     */
    public void goToMainIntentSave() {
        if (p.getID() == null) {
            p.setName(title.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("title_photo", this.p.getName());
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.FRANCE);
            String date = df.format(Calendar.getInstance().getTime());
            intent.putExtra("date_photo", date);
            intent.putExtra("path", p.getAddress());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            p.setName(title.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("title_photo", this.p.getName());
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.FRANCE);
            String date = df.format(Calendar.getInstance().getTime());
            intent.putExtra("id", p.getID());
            intent.putExtra("path", p.getAddress());
            intent.putExtra("url", p.getUrl());
            intent.putExtra("positionPhoto", this.position);
            setResult(3, intent);
            finish();
        }
    }

    /**
     * Method onClick to check the button clicked and interact according to the purpose of the
     * button.
     * @param v View
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.PhotoDeleteButton) {
            goToMainIntentNoSave();
        }
        if (v.getId() == R.id.PhotoSaveButton) {
            goToMainIntentSave();
        }
        if (v.getId() == R.id.PhotoShareButton) {
            goToShareIntent();
        }
    }

    /**
     * Method to go to share intent, in with one the user can share the note.
     */
    private void goToShareIntent() {
        Drawable drawable = miniatura.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        try {
            File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + "share.jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.example.android.fileprovider", file);

            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");

            Intent chooser = Intent.createChooser(intent, "Share...");

            List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                this.grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            startActivity(chooser);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}