package com.francivaldo.drausioacademia.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.francivaldo.drausioacademia.main.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDB {
    FirebaseFirestore db;
    List<User> userList;
    public LogClass logClass;
    public final String INFOR = "INFOR",MESES = "MESES",TREINO = "TREINO",ID = "ID";
    private boolean isUserRoot = false;
    public FirebaseDB(){
        db = FirebaseFirestore.getInstance();
        checkUID();
    }
    private void checkUID(){
        db.collection("userUID")
            .document("HhGADfcW0cgnguRsTIBy").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot doc) {
                        isUserRoot = setUserList(doc.getString("uid"), FirebaseAuth.getInstance().getUid());
                    }
                });
    }
    private boolean setUserList(CharSequence s1,CharSequence s2){
        for (int i = 0; i < s1.length(); i++) {
            if(i < s2.length() && s1.charAt(i) != s2.charAt(i)){
                return false;
            }
        }
        return true;
    }
    public void load(){
        userList = new ArrayList<>();
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc: task.getResult()) {
                            if(doc.exists()) {
                                User user = new User(doc.getString(INFOR), doc.getString(ID));
                                user.setMeses(doc.getString(MESES));
                                user.setTreino(doc.getString(TREINO));
                                userList.add(user);
                            }
                        }
                        Log.d("Database :","loaded");
                        if(logClass != null)
                            logClass.onSucess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Database :","Load error ! ~ "+e.getMessage());
                        if(logClass != null)
                            logClass.onFailure();
                    }
                });
    }
    public List<User> get(){
        return userList;
    }
    public void add(User user){
        if(isUserRoot == false)
            return;
        Map<String,Object> doc = new HashMap<>();
        DocumentReference dr = db.collection("user").document();

        doc.put(ID,dr.getId());
        doc.put(INFOR,user.getPIString());
        doc.put(MESES,user.getMesesString());
        doc.put(TREINO,user.getTreinoString());

        dr.set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Database :","added");
                        if(logClass != null)
                            logClass.onSucess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Database :","added erro ! ~ "+e.getMessage());
                        if(logClass != null)
                            logClass.onFailure();
                    }
                });
        load();
    }
    public void update(User user){
        if(isUserRoot == false)
            return;
        Map<String,Object> doc = new HashMap<>();
        doc.put(ID,user.getId());
        doc.put(INFOR,user.getPIString());
        doc.put(MESES,user.getMesesString());
        doc.put(TREINO,user.getTreinoString());
        Log.d("Database :",user.getId());
        db.collection("user").document(user.getId())
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database :","updated");
                        if(logClass != null)
                            logClass.onSucess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Database :","update erro ! ~ "+e.getMessage());
                        if(logClass != null)
                            logClass.onFailure();
                    }
                });
        load();
    }
    public void delet(User user){
        if(isUserRoot == false)
            return;
        db.collection("user").document(user.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database :","deleted");
                        if(logClass != null)
                            logClass.onSucess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Database :","delet erro ! ~ "+e.getMessage());
                        if(logClass != null)
                            logClass.onFailure();
                    }
                });
        load();
    }
}
