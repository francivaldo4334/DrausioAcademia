package com.francivaldo.drausioacademia.main;

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

public class Cadastro extends AppCompatActivity {
    private EditText edt_email,edt_senha,edt_confirm_senha;
    String usuarioId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        edt_email = findViewById(R.id.edt_email_cadas);
        edt_senha = findViewById(R.id.edt_senha_cadas);
        edt_confirm_senha = findViewById(R.id.edt_senha_candas_comfir);
    }

    public void onCreate(View view) {
        String _Email,_Senha,_Confirm_senha;
        _Email = edt_email.getText().toString();
        _Senha = edt_senha.getText().toString();
        _Confirm_senha = edt_confirm_senha.getText().toString();
        if(!TextUtils.isEmpty(_Email)&&!TextUtils.isEmpty(_Senha)&&!TextUtils.isEmpty(_Confirm_senha)&&_Senha.equals(_Confirm_senha)){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(_Email, _Senha)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

//                                SalvarDadosUsuario();

                                Toast.makeText(Cadastro.this, "cadastro realizado com sucesso",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Cadastro.this,Login.class));
                                finish();
                            } else {
                                String erro = task.getException().getMessage();
                                Toast.makeText(Cadastro.this, "" + erro, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}