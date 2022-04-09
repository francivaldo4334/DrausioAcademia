package com.francivaldo.drausioacademia.ficha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.database.LogClass;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.adapter.AdapterTreino;
import com.francivaldo.drausioacademia.main.model.FichaTreino;
import com.francivaldo.drausioacademia.main.model.User;

import java.util.ArrayList;
import java.util.List;

public class ActivityTreino extends AppCompatActivity {
    User user;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AdapterTreino adapter;
    List<FichaTreino.Treino> list = new ArrayList<>();
    int viewID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);
        Bundle extras = getIntent().getExtras();
        user = new User(extras.getString(Main.db.INFOR),extras.getString(Main.db.ID));
        user.setTreino(extras.getString(Main.db.TREINO));
        user.setMeses(extras.getString(Main.db.MESES));
        TextView textViewname = findViewById(R.id.name_treino_atv_treino);

        viewID = extras.getInt("NAME");
        switch (viewID){
            case R.id.treino_1:
                textViewname.setText("Peito");
                list = user.getTreino().treinoPeito;
                break;
            case R.id.treino_2:
                textViewname.setText("Constas");
                list = user.getTreino().treinoCostas;
                break;
            case R.id.treino_3:
                textViewname.setText("Biceps");
                list = user.getTreino().treinoBiceps;
                break;
            case R.id.treino_4:
                textViewname.setText("Ante Braço");
                list = user.getTreino().treinoAnBraco;
                break;
            case R.id.treino_5:
                textViewname.setText("Pernas");
                list = user.getTreino().treinoPernas;
                break;
            case R.id.treino_6:
                textViewname.setText("Ombro");
                list = user.getTreino().treinoOmbro;
                break;
            case R.id.treino_7:
                textViewname.setText("Triceps");
                list = user.getTreino().treinoTriceps;
                break;
            case R.id.treino_8:
                textViewname.setText("Abdomen para vertebras");
                list = user.getTreino().treinoAbVert;
                break;
            case R.id.treino_9:
                textViewname.setText("Coxas e gluteus");
                list = user.getTreino().treinoCoxGlu;
                break;
        }
        initRecyclerView();
    }
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.list_treino);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<User> userList = Main.db.get();
        adapter =  new AdapterTreino(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void onSaveTreino(View view){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("gravando...");
        pd.show();
        switch (viewID){
            case R.id.treino_1:
                user.getTreino().treinoPeito = list;
                break;
            case R.id.treino_2:
                user.getTreino().treinoCostas = list;
                break;
            case R.id.treino_3:
                user.getTreino().treinoBiceps = list;
                break;
            case R.id.treino_4:
                user.getTreino().treinoAnBraco = list;
                break;
            case R.id.treino_5:
                user.getTreino().treinoPernas = list;
                break;
            case R.id.treino_6:
                user.getTreino().treinoOmbro = list;
                break;
            case R.id.treino_7:
                user.getTreino().treinoTriceps = list;
                break;
            case R.id.treino_8:
                user.getTreino().treinoAbVert = list;
                break;
            case R.id.treino_9:
                user.getTreino().treinoCoxGlu = list;
                break;
        }
        Main.db.logClass = new LogClass() {
            @Override
            public void onSucess() {
                pd.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure() {
                pd.dismiss();
                Toast.makeText(ActivityTreino.this, "erro au salvar!", Toast.LENGTH_SHORT).show();
            }
        };
        Main.db.update(user);
    }
    public void onIcBack(View view) {
        onBackPressed();
    }
    public void onBackPressed() {
        super.onBackPressed();
        Main.db.logClass = null;
        Intent intent = new Intent(this, ActivityTreinos.class);
        intent.putExtra(Main.db.ID,user.getId());
        intent.putExtra(Main.db.INFOR,user.getPIString());
        intent.putExtra(Main.db.TREINO,user.getTreinoString());
        intent.putExtra(Main.db.MESES,user.getMesesString());
        startActivity(intent);
        finish();
    }
}