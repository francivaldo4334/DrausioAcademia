package com.francivaldo.drausioacademia.ficha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.aluno.AlunoInfor;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.model.User;

public class ActivityTreinos extends AppCompatActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);
        Bundle extras = getIntent().getExtras();
        user = new User(extras.getString(Main.db.INFOR),extras.getString(Main.db.ID));
        user.setTreino(extras.getString(Main.db.TREINO));
        user.setMeses(extras.getString(Main.db.MESES));
    }


    public void onIcBack(View view) {
        onBackPressed();
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, AlunoInfor.class);
        intent.putExtra(Main.db.ID,user.getId());
        intent.putExtra(Main.db.INFOR,user.getPIString());
        intent.putExtra(Main.db.TREINO,user.getTreinoString());
        intent.putExtra(Main.db.MESES,user.getMesesString());
        startActivity(intent);
        finish();
    }

    public void onActivityTreino(View view) {
        Intent intent = new Intent(this, ActivityTreino.class);
        intent.putExtra("NAME",view.getId());
        intent.putExtra(Main.db.ID,user.getId());
        intent.putExtra(Main.db.INFOR,user.getPIString());
        intent.putExtra(Main.db.TREINO,user.getTreinoString());
        intent.putExtra(Main.db.MESES,user.getMesesString());
        startActivity(intent);
        finish();
    }
}