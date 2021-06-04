package com.example.pis_entrega1.Model;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import com.example.pis_entrega1.*;

import androidx.annotation.NonNull;

import com.example.pis_entrega1.Activities.AuthActivity;
import com.example.pis_entrega1.Note.Notes;
import com.example.pis_entrega1.Note.Photo;
import com.example.pis_entrega1.Note.Recording;
import com.example.pis_entrega1.Note.Text;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to init de database of the app
 */
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

    /**
     * Construct method set variables into his values
     * @param Email Email to log in
     * @param Password Password of the user
     * @param Opcion Can be: logIn or Register
     * @param t Auth activity to show error messages or confirmation register
     */
    public DatabaseAdapter(String Email, String Password, String Opcion,AuthActivity t){
        databaseAdapter = this;
        this.email = Email;
        this.password = Password;
        this.opcion = Opcion;
        FirebaseFirestore.setLoggingEnabled(true);
        this.n = t;
        initFirebase();
    }

    /**
     * Method to set the interface
     * @param listener listener to set
     */
    public void setInterface(vmInterface listener){
        this.listener = listener;
    }

    /**
     * Method to delete a note with his id
     * @param id id of the note to delete
     */
    public void delete(String id) {
        DatabaseAdapter.db.collection("Notes " + email).document(id).delete();
    }

    /**
     * Interface created to set the Array List of Notes
     */
    public interface vmInterface{
        void setCollection(ArrayList<Notes> ac);
    }

    /**
     * Method to initialize the firebase with the user given by the client
     * 2 possible function register or log in
     */
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

    /**
     * Method to get the user's current note collection in the firebase and set it in the viewModel
     * arrayList by the interface
     */
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
                            Text t = new Text(document.getString("name"), document.getString("bodytext"), document.getString("date"), document.getId());
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

    /**
     * Method to save an audio document in the firebase user collection
     * @param name Title of the AudioNote
     * @param Path Local Path to the Audio note
     * @param date Date of the Audio Note
     * @param url Address access to the audio note from inside the firebase storage
     */
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

    /**
     * Method to safe the Audio File in firebase Storage
     * firebase Audio saved on folder audio/
     * @param name Title of the AudioNote
     * @param path Local Path to the Audio Note
     * @param date Date of the Audio Note
     */
    public void saveAudioDocumentWithFile (String name, String path, String date) {

        Uri file = Uri.fromFile(new File(path));
        StorageReference storageRef = storage.getReference();
        StorageReference audioRef = storageRef.child("audio"+File.separator);
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

    /**
     *  Method to save a text Note in the firebase user collection
     * @param name Title of the Text Note
     * @param text Text of the Text Note
     * @param data Date of the Text Note
     */
    public void saveTextDocument (String name, String text, String data) {

        // Create a new user with a first and last name
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Texto");
        note.put("name", name);
        note.put("bodytext", text);
        note.put("date", data);

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

    /**
     * Method to update changes in a Photo Note
     * @param name Title of the Photo Note
     * @param address Local Address of the Photo Note
     * @param id Identifier of the note inside the firebase collection
     * @param date Date of the modification
     * @param url Address access to the Photo Note from inside the firebase storage
     */
    public void actualizarPhotoNote(String name, String address, String id, String date, String url) {
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Foto");
        note.put("name", name);
        note.put("path", address);
        note.put("date", date);
        note.put("url", url);

        db.collection("Notes "+ email).document(id).update(note);
    }

    /**
     * Method to update changes in a Audio Note
     * @param name Title of the Audio Note
     * @param address Local Address of the Audio Note
     * @param id Identifier of the note inside the firebase collection
     * @param date Date of the modification
     * @param url Address access to the Audio Note from inside the firebase storage
     */
    public void actualizarAudioNote(String name, String address, String id, String date, String url) {
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Audio");
        note.put("name", name);
        note.put("path", address);
        note.put("date", date);
        note.put("url", url);

        db.collection("Notes "+ email).document(id).update(note);
    }

    /**
     * Method to update changes in a Text Note
     * @param name Title of the Text Note
     * @param text Text of the Text Note
     * @param data Data of the modification
     * @param id Identifier of the note inside the firebase collection
     */
    public void actualizarTextNote(String name, String text, String data, String id){
        Map<String, Object> note = new HashMap<>();
        note.put("tipo", "Texto");
        note.put("name", name);
        note.put("bodytext", text);
        note.put("date", data);

        db.collection("Notes "+ email).document(id).update(note);
    }

    /**
     * Method to save a Photo Note in firebase user collection
     * @param name Name of the Photo Note
     * @param date Creation date of the Photo Note
     * @param path Local path to the Photo
     * @param url Address access to the Photo Note from inside the firebase storage
     */
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

    /**
     * Method to safe the Photo File in firebase Storage
     * firebase Audio saved on folder photo/
     * @param name Title of the Photo Note
     * @param path Local Path to the Photo Note
     * @param date Date of the Photo Note
     */
    public void savePhotoDocumentWithFile (String name, String path, String date) {

        Uri file = Uri.fromFile(new File(path));
        StorageReference storageRef = storage.getReference();
        StorageReference photoRef = storageRef.child("photo"+File.separator);
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
                    savePhotoDocument(name, date, path, downloadUri.toString());
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

    /**
     * Method to send an email to the user for password recovery
     * @param email Email to send te recovery password
     */
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

    /**
     * Method to download an Audio Record from the firebase storage
     * @param url Address access to the Audio from inside the firebase storage
     * @return Local path where audio has downloaded
     */
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

    /**
     * Method to download a Photo from the firebase storage
     * @param url Address access to the Photo from inside the firebase storage
     * @return Local path where audio has downloaded
     */
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
