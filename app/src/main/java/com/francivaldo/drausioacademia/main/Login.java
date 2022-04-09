package com.francivaldo.drausioacademia.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.francivaldo.drausioacademia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText edt_Email,edt_Senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_Email = findViewById(R.id.edt_email_cadas);
        edt_Senha = findViewById(R.id.edt_senha_cadas);
    }

    public void onLogin(View view) {
        String _email,_senha;
        _email = edt_Email.getText().toString();
        _senha = edt_Senha.getText().toString();


        if(!TextUtils.isEmpty(_email) || !TextUtils.isEmpty(_senha)) {

            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Logando...");
            pd.show();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(_email, _senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pd.dismiss();
                        Intent intent = new Intent(Login.this, Main.class);
                        startActivity(intent);
                        finish();
                    } else {
                        pd.dismiss();
                        String erro = task.getException().getMessage();
                        Toast.makeText(Login.this, "" + erro, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onCreateCadas(View view) {
        Intent intent = new Intent(Login.this, Cadastro.class);
        startActivity(intent);
        finish();
    }
}