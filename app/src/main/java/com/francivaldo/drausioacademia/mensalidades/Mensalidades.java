package com.francivaldo.drausioacademia.mensalidades;

import android.os.Bundle;
import android.text.format.Time;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.adapter.AdapterMeses;
import com.francivaldo.drausioacademia.main.model.Mdate;
import com.francivaldo.drausioacademia.main.model.Meses;
import com.francivaldo.drausioacademia.main.model.User;

import java.util.ArrayList;
import java.util.List;

public class Mensalidades extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Meses.Mes> meses;
    AdapterMeses adapter;
    Time today;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensalidades);
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        setUserList();
        initRecyclerView();
    }
    private void setUserList(){
        meses = new ArrayList<>();
        meses.add(new Meses.Mes("janeiro",getIsCheck(0)));
        meses.add(new Meses.Mes("fevereiro",getIsCheck(1)));
        meses.add(new Meses.Mes("março",getIsCheck(2)));
        meses.add(new Meses.Mes("abril",getIsCheck(3)));
        meses.add(new Meses.Mes("maio",getIsCheck(4)));
        meses.add(new Meses.Mes("junho",getIsCheck(5)));
        meses.add(new Meses.Mes("julho",getIsCheck(6)));
        meses.add(new Meses.Mes("agosto",getIsCheck(7)));
        meses.add(new Meses.Mes("setembro",getIsCheck(8)));
        meses.add(new Meses.Mes("outubro",getIsCheck(9)));
        meses.add(new Meses.Mes("novembro",getIsCheck(10)));
        meses.add(new Meses.Mes("dezembro",getIsCheck(11)));
    }
    private boolean getIsCheck( int mesId){
        String dateid = "1/" + (mesId+1) + "/" + today.year;
        //true all users initd >= atuld && check
        for (User user: Main.db.get()) {
            if(dateAfter(new Mdate(dateid),user.getDate()) && !user.getMeses().mes[mesId].isCheck)
                return false;

        }
        return true;
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
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.list_mes);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new AdapterMeses(meses);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onIcBack(View view) {
        finish();
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUserList();
        initRecyclerView();
    }
}