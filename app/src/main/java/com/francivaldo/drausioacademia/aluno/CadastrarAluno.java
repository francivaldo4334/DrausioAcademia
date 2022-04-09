package com.francivaldo.drausioacademia.aluno;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.database.LogClass;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.model.Mdate;
import com.francivaldo.drausioacademia.main.model.User;
import com.google.gson.Gson;

import java.util.Calendar;

public class CadastrarAluno extends AppCompatActivity {
    EditText edt_name, date;
    ProgressDialog pd;
    Time today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aluno);
        edt_name = findViewById(R.id.edt_nome);
        date = findViewById(R.id.edt_data);
        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }
                    else{
//                        This part makes sure that when we finish entering numbers
//                        the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));
//
                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        date.addTextChangedListener(tw);
        pd = new ProgressDialog(this);

        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        String month = String.valueOf(today.month+1) ;
        month = today.month+1 < 10?"0"+month:month;
        String day = String.valueOf(today.monthDay);
        day = today.monthDay < 10?"0"+day:day;
        String date;
        date = day + month + today.year;
        this.date.setText(date);
    }

    public void onIcBack(View view) {
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    public void onConfirmarCadastro(View view) {
        String _name,_date;
        _name = edt_name.getText().toString();
        _date = date.getText().toString();
        if(!TextUtils.isEmpty(_name) && !TextUtils.isEmpty(_date)) {
            //salvar no database
            uploadData(new User(_name,new Gson().toJson(new Mdate(_date)),null));

            String month = String.valueOf(today.month+1) ;
            month = today.month+1 < 10?"0"+month:month;
            String day = String.valueOf(today.monthDay);
            day = today.monthDay < 10?"0"+day:day;
            String date;
            date = day + month + today.year;
            edt_name.setText("");

        }
        else {
            Toast.makeText(this,R.string.alert_menssage,Toast.LENGTH_SHORT).show();
        }
    }
    public void onCanselarCadastro(View view) {
        finish();
    }
    private void uploadData(User user){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Salvando...");
        pd.show();
        Main.db.logClass = new LogClass() {
            @Override
            public void onSucess() {
                pd.dismiss();
                Toast.makeText(CadastrarAluno.this, "inscrição salva", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                pd.dismiss();
                Toast.makeText(CadastrarAluno.this, "erro au salvar inscrição", Toast.LENGTH_SHORT).show();
            }
        };
        Main.db.add(user);

    }
}