package com.francivaldo.drausioacademia.main;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.aluno.Alunos;
import com.francivaldo.drausioacademia.aluno.CadastrarAluno;
import com.francivaldo.drausioacademia.database.FirebaseDB;
import com.francivaldo.drausioacademia.database.LogClass;
import com.francivaldo.drausioacademia.mensalidades.Mensalidades;
import com.google.firebase.auth.FirebaseAuth;

import java.net.InetAddress;

public class Main extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    public  static FirebaseDB db;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Conectando...");
        pd.show();
        db = new FirebaseDB();
        db.logClass = new LogClass() {
            @Override
            public void onSucess() {
                pd.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(Main.this, "erro na conexão!", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        };
        db.load();
        if (!checkPermission())
            requestPermission();
        if(!isNetworkConnected() && !isInternetAvailable()){
            Toast.makeText(this, "você está desconectado da internet!", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    public boolean isInternetAvailable(){
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");
        }catch (Exception e){
            return false;
        }
    }
    public void onAlunos(View view){
        db.logClass = null;
        Intent intent = new Intent(Main.this, Alunos.class);
        startActivity(intent);
    }
    public void onMensalidades(View view){
        db.logClass = null;
        startActivity(new Intent(Main.this, Mensalidades.class));
    }

    public void onCriarCadastro(View view) {
        db.logClass = null;
        startActivity(new Intent(Main.this, CadastrarAluno.class));
    }

    public void onShere(View view) {
        //share link of app
        String string = getString(R.string.app_link);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, string);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
    //permmission
    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void onLogout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_log_fin);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Main.this.finish();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
}