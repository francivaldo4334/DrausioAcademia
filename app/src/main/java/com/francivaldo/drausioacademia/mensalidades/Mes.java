package com.francivaldo.drausioacademia.mensalidades;

import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.adapter.AdapterUsersMEs;
import com.francivaldo.drausioacademia.main.model.Mdate;
import com.francivaldo.drausioacademia.main.model.User;

import java.util.ArrayList;
import java.util.List;

public class Mes extends AppCompatActivity {
    TextView title;
    int dateId;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AdapterUsersMEs adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes);
        title = findViewById(R.id.txt_name_mes);
        Bundle extras = getIntent().getExtras();
        String dateName =extras.getString("NAME");
        title.setText(dateName);
        switch (dateName){
            case "janeiro":
                dateId = 0;
                break;
            case "fevereiro":
                dateId = 1;
                break;
            case "março":
                dateId = 2;
                break;
            case "abril":
                dateId = 3;
                break;
            case "maio":
                dateId = 4;
                break;
            case "junho":
                dateId = 5;
                break;
            case "julho":
                dateId = 6;
                break;
            case "agosto":
                dateId = 7;
                break;
            case "setembro":
                dateId = 8;
                break;
            case "outubro":
                dateId = 9;
                break;
            case "novembro":
                dateId = 10;
                break;
            case "dezembro":
                dateId = 11;
                break;
        }
        initRecyclerView();
    }
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.list_mensalidades_alunos_mes);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        List<User> userList = new ArrayList<>();
        String dateid = "1/" + (dateId+1) + "/" + today.year;
        for (User user: Main.db.get()) {
            if(dateAfter(new Mdate(dateid),user.getDate()))
                userList.add(user);
        }
        adapter =  new AdapterUsersMEs(userList, dateId);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private boolean dateAfter(Mdate date1, Mdate date2){
        int value1 = 0,value2 = 0;
        value1 = date1.month + date1.year;
        value2 = date2.month + date2.year;
        if(value1 >= value2)
            return true;
        else
            return false;
    }

    public void onIcBack(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}