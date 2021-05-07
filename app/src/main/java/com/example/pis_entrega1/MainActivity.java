package com.example.pis_entrega1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.components.Lazy;

import java.util.Collections;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    boolean clicked;
    FloatingActionButton fabText, fabAudio, fabPhoto;
    ExtendedFloatingActionButton addNote;
    TextView addText, addAudio, addPhoto;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private RecyclerView mRecyclerView;

    private MyAdapter mAdapter;

    public NotesContainer nc;
    public Context parentContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nc = new NotesContainer();
        parentContext = this.getBaseContext();
        setContentView(R.layout.activity_main);

        addNote = findViewById(R.id.add_fab);

        fabText = findViewById(R.id.TextButton);
        fabAudio = findViewById(R.id.AudioButton);
        fabPhoto = findViewById(R.id.CameraButton);

        addText = findViewById(R.id.addText);
        addAudio = findViewById(R.id.addAudio);
        addPhoto = findViewById(R.id.addPhoto);

        fabText.setVisibility(View.GONE);
        fabAudio.setVisibility(View.GONE);
        fabPhoto.setVisibility(View.GONE);
        addText.setVisibility(View.GONE);
        addAudio.setVisibility(View.GONE);
        addPhoto.setVisibility(View.GONE);

        clicked = false;

        addNote.shrink();

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked) {

                    fabText.show();
                    fabAudio.show();
                    fabPhoto.show();
                    addText.setVisibility(View.VISIBLE);
                    addAudio.setVisibility(View.VISIBLE);
                    addPhoto.setVisibility(View.VISIBLE);

                    addNote.extend();

                    clicked = true;
                } else {
                    fabText.hide();
                    fabAudio.hide();
                    fabPhoto.hide();
                    addText.setVisibility(View.GONE);
                    addAudio.setVisibility(View.GONE);
                    addPhoto.setVisibility(View.GONE);

                    addNote.shrink();

                    clicked = false;
                }
            }
        });

        fabText.setOnClickListener(this);
        fabAudio.setOnClickListener(this);
        fabPhoto.setOnClickListener(this);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        this.setTable();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (intent.getStringExtra("title") != null) {
                    Text text_temp = new Text(intent.getLongExtra("date", 0), intent.getStringExtra("title"), intent.getStringExtra("text"));
                    this.nc.addTextNote(text_temp, intent.getIntExtra("positionText", -1));
                    this.setTable();
                } else if (intent.getStringExtra("title_audio_main") != null) {
                    Recording recordingTemp = new Recording(intent.getLongExtra("date_audio_main", 0), intent.getStringExtra("title_audio_main"), intent.getStringExtra("Adress_main"));
                    this.nc.addAudioNote(recordingTemp, intent.getIntExtra("positionAudio", -1));
                    this.setTable();
                } else if (intent.getStringExtra("title_photo_main") != null) {
                    Photo photoTemp = new Photo(intent.getLongExtra("date_photo_main", 0), intent.getStringExtra("title_photo_main"), intent.getByteArrayExtra("byteImage_main"));
                    this.nc.addPhotoNote(photoTemp);
                    this.setTable();
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (intent.getIntExtra("positionDelete", -1) != -1){
                    this.nc.deleteNote(intent.getIntExtra("positionDelete", -1));
                    this.setTable();
                }
            }
        }
    }

    public void goToTextNote() {
        Intent i = new Intent(this, TextNote.class);
        startActivityForResult(i, 1);
    }

    public void goTOAudioNote() {
        Intent i = new Intent(this, AudioNote.class);
        startActivityForResult(i, 1);
    }

    public void goToCameraNote() {
        Intent i = new Intent(this, PhotoNote.class);
        startActivityForResult(i, 1);
    }

    @Override
    public void onClick(View v) {
        if (R.id.TextButton == v.getId()) {
            goToTextNote();
        }
        if (R.id.AudioButton == v.getId()) {
            goTOAudioNote();
        }
        if (R.id.CameraButton == v.getId()) {
            goToCameraNote();
        }
    }

    void setTable () {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, nc);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nc.get(recyclerView.getChildAdapterPosition(v)) instanceof Text){
                    passDataText((Text) nc.get(recyclerView.getChildAdapterPosition(v)), recyclerView.getChildAdapterPosition(v));
                }else if(nc.get(recyclerView.getChildAdapterPosition(v)) instanceof Recording){
                    passDataAudio((Recording) nc.get(recyclerView.getChildAdapterPosition(v)), recyclerView.getChildAdapterPosition(v));
                }else{
                    passDataPhoto((Photo) nc.get(recyclerView.getChildAdapterPosition(v)), recyclerView.getChildAdapterPosition(v));
                }
                Toast.makeText(getApplicationContext(),"Selección: "+ nc.get(recyclerView.getChildAdapterPosition(v)).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    void passDataText (Text text, int position) {
        Intent i = new Intent(this, TextNote.class);
        i.putExtra("newTitleText", text.getName());
        i.putExtra("newTextText", text.getText());
        i.putExtra("positionText", position);
        startActivityForResult(i, 1);
    }

    void passDataAudio (Recording recording, int position){
        Intent n1 = new Intent(this, AudioRecorded.class);
        n1.putExtra("newTitleAudio", recording.getName());
        n1.putExtra("Adress", recording.getAddress());
        n1.putExtra("positionAudio", position);
        startActivityForResult(n1, 1);
    }

    void passDataPhoto (Photo photo, int position){
        Intent n = new Intent(this, PhotoTaken.class);
        n.putExtra("newTitlePhoto", photo.getName());
        n.putExtra("photo", photo.miniatura);
        n.putExtra("positionPhoto", position);
        startActivityForResult(n, 1);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFrlg = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            return makeMovementFlags(dragFrlg, 0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            // Obtenga el viewHolder del elemento arrastrado actualmente
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(nc.getContainer(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(nc.getContainer(), i, i - 1);
                }
            }
            nc.notifyItemMoved(fromPosition, toPosition);
            mAdapter.setmNotesContainer(nc);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    });
}