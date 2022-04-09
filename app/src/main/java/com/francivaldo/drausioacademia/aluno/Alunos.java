package com.francivaldo.drausioacademia.aluno;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.adapter.AdapterAlunos;
import com.francivaldo.drausioacademia.main.model.User;

import java.util.ArrayList;
import java.util.List;

public class Alunos extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AdapterAlunos adapter;
    EditText edt_search;
    TextView alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        edt_search = findViewById(R.id.edt_search);
        alert = findViewById(R.id.alertofsearch);
        initRecyclerView();
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    public void filter(String text){
        ArrayList<User> filterList = new ArrayList<>();
        boolean b = true;
        for (User item: Main.db.get()) {
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
                b = false;
            }
        }
        if(b)
            alert.setVisibility(View.VISIBLE);
        else
            alert.setVisibility(View.INVISIBLE);
        adapter.filterList(filterList);
    }
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.list_alunos);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<User> userList = Main.db.get();
        adapter =  new AdapterAlunos(this,userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onIcBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Main.db.logClass = null;
        finish();
    }
}