package com.example.pis_entrega1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Continuation;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class DatabaseAdapter extends Activity{

    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private String email,password,opcion;
    private AuthActivity n;


    public static vmInterface listener;
    public static DatabaseAdapter databaseAdapter;
    public DatabaseAdapter(){}

    public DatabaseAdapter(String Email, String Password, String Opcion,AuthActivity t){
        databaseAdapter = this;
        this.email = Email;
        this.password = Password;
        this.opcion = Opcion;
        FirebaseFirestore.setLoggingEnabled(true);
        this.n = t;
        initFirebase();
    }

    public void setInterface(vmInterface listener){
        this.listener = listener;
    }

    public void logOut() {

        mAuth.signOut();
    }

    public void delete(String id) {
        DatabaseAdapter.db.collection("Notes " + email).document(id).delete();
    }


    public interface vmInterface{

        void setCollection(ArrayList<Notes> ac);
        void setToast(String s);
    }
    public void initFirebase(){
        if(opcion.equals("register")){
            mAuth.createUserWithEmailAndPassword(this.email,this.password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInEmailPassword:success");
                        user = mAuth.getCurrentUser();
                        n.goToMainIntent();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInEmailPassword:failure", task.getException());
                        n.ErrorLogin(task.getException().toString());
                    }
                }
            });
        }else{
            mAuth.signInWithEmailAndPassword(this.email,this.password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInEmailPassword:success");
                        user = mAuth.getCurrentUser();
                        n.goToMainIntent();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInEmailPassword:failure", task.getException());
                        n.ErrorLogin(task.getException().toString());
                    }
                }
            });
        }
    }

    public void getCollection(){
        Log.d(TAG,"updateNotes");
        DatabaseAdapter.db.collection("Notes " + email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    ArrayList<Notes> retrieved_ac = new ArrayList<Notes>() ;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String tipo = document.getString("tipo");
                        if (tipo.equals("Texto")){
                            Text t = new Text(document.getString("name"), document.getString("bodytext"), document.getString("url"), document.getId());
                            t.setDate(document.getString("date"));
                            retrieved_ac.add(t);
                            Log.e("id", document.getId());
                        }if(tipo.equals("Audio")){
                            Recording r = new Recording(document.getString("name"), document.getString("path"),document.getId(),document.getString("date"), document.getString("url"));
                            retrieved_ac.add(r);
                            Log.e("id", document.getId());
                        }if(tipo.equals("Foto")){
                            Photo f = new Photo(document.getString("name"), document.getString("path"),document.getId(), document.getString("date"), document.getString("url"));
                            retrieved_ac.add(f);
                        }
                        Log.d(TAG, document.toString());
                    }
                    listener.setCollection(retrieved_ac);
                }
            }
        });
    }


    public void saveAudioDocument (String name, String Path, String date, String url) {

        // Create a new user with a first and last name
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Audio");
        note.put("name", name);
        note.put("path", Path);
        note.put("date", date);
        note.put("url", url);

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Notes " + email)
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        getCollection();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void saveAudioDocumentWithFile (String name, String path, String date) {

        Uri file = Uri.fromFile(new File(path));
        StorageReference storageRef = storage.getReference();
        StorageReference audioRef = storageRef.child("audio"+File.separator+file.getLastPathSegment());
        UploadTask uploadTask = audioRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return audioRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    saveAudioDocument(name, path, date, downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        });
    }

    public void saveTextDocument (String name, String text, String data, String url) {

        // Create a new user with a first and last name
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Texto");
        note.put("name", name);
        note.put("bodytext", text);
        note.put("date", data);
        note.put("url", url);

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Notes "+ email)
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        getCollection();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    public void actualizarPhotoNote(String name, String address, String id, String date, String url) {
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Foto");
        note.put("name", name);
        note.put("path", address);
        note.put("date", date);
        note.put("url", url);

        db.collection("Notes "+ email).document(id).update(note);
    }

    public void actualizarAudioNote(String name, String address, String id, String date, String url) {
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Audio");
        note.put("name", name);
        note.put("path", address);
        note.put("date", date);
        note.put("url", url);

        db.collection("Notes "+ email).document(id).update(note);
    }

    public void actualizarTextNote(String name, String text, String data, String path, String id){
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Texto");
        note.put("name", name);
        note.put("bodytext", text);
        note.put("date", data);
        note.put("url", path);

        db.collection("Notes "+ email).document(id).update(note);
    }

    public void saveTextDocumentWithFile (String name, String text, String path, String data) {

        Uri file = Uri.fromFile(new File(path));
        StorageReference storageRef = storage.getReference();
        StorageReference textRef = storageRef.child("text"+File.separator+file.getLastPathSegment());
        UploadTask uploadTask = textRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return textRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    saveTextDocument(name, text, data, downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        });
    }

    public void savePhotoDocument (String name, String date, String path, String url) {

        // Create a new user with a first and last name
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Foto");
        note.put("name", name);
        note.put("date", date);
        note.put("path", path);
        note.put("url", url);

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Notes "+email)
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        getCollection();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

            public void savePhotoDocumentWithFile (String id, String path, String date) {

                Uri file = Uri.fromFile(new File(path));
                StorageReference storageRef = storage.getReference();
                StorageReference photoRef = storageRef.child("photo"+File.separator+file.getLastPathSegment());
                UploadTask uploadTask = photoRef.putFile(file);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return photoRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            savePhotoDocument(id, date, path, downloadUri.toString());
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });


        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        });
    }

    public HashMap<String, String> getDocuments () {
        db.collection("Notes "+email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return new HashMap<>();
    }

    public void ForgotPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    public String descargarAudioDatabase(String url){
        StorageReference urlArchivo = storage.getReferenceFromUrl(url);
        File localFile = null;
        try {
            localFile = File.createTempFile("music", "mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlArchivo.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d("download", "archivo descargado");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("download", "error");
            }
        });
        return localFile.getAbsolutePath();
    }

    public String descargarPhotoDatabase(String url){
        StorageReference urlArchivo = storage.getReferenceFromUrl(url);
        File localFile = null;
        try {
            localFile = File.createTempFile("image", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlArchivo.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d("download", "archivo descargado");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("download", "error");
            }
        });
        return localFile.getAbsolutePath();
    }

}
