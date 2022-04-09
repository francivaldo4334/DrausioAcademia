package com.francivaldo.drausioacademia.ficha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.aluno.AlunoInfor;
import com.francivaldo.drausioacademia.database.LogClass;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.model.Mdate;
import com.francivaldo.drausioacademia.main.model.PersonalInformation;
import com.francivaldo.drausioacademia.main.model.User;
import com.google.gson.Gson;

public class ActivityUserInfor extends AppCompatActivity {
    Dialog dialog;
    int viewID;
    EditText editText;
    PersonalInformation pi;
    User user;
    CheckBox checkBox1,checkBox2,checkBox3;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_edit_infor);
        editText = dialog.findViewById(R.id.edt_text_infor);
        checkBox1 = findViewById(R.id.checkbox_1);
        checkBox2 = findViewById(R.id.checkbox_2);
        checkBox3 = findViewById(R.id.checkbox_3);
        Bundle extras = getIntent().getExtras();
        user = new User(extras.getString(Main.db.INFOR),extras.getString(Main.db.ID));
        user.setTreino(extras.getString(Main.db.TREINO));
        user.setMeses(extras.getString(Main.db.MESES));
        pi = user.getPi();
        pd = new ProgressDialog(this);
        pd.setTitle("gravando...");
        initComponents();
    }
    public void onDialogEdit(View view){
        viewID = view.getId();
        if(viewID == R.id.infor_date_init ||viewID == R.id.infor_cpf ||viewID == R.id.infor_cantato ||viewID == R.id.infor_n_vez_semana )
            editText.setInputType(EditText.AUTOFILL_TYPE_DATE);
        else
            editText.setInputType(EditText.AUTOFILL_TYPE_TEXT);
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        Main.db.logClass = null;
        Intent intent = new Intent(this, AlunoInfor.class);
        intent.putExtra(Main.db.ID,user.getId());
        intent.putExtra(Main.db.INFOR,user.getPIString());
        intent.putExtra(Main.db.TREINO,user.getTreinoString());
        intent.putExtra(Main.db.MESES,user.getMesesString());
        startActivity(intent);
        finish();
    }
    public void onComfirmEditInfor(View view) {
        TextView textView = (TextView) findViewById(viewID);
        textView.setText( editText.getText());
        setPI();
        editText.setText("");
        dialog.dismiss();
    }
    public void onCancelEditInfor(View view){
        dialog.dismiss();
    }
    public void onIcBack(View view) {
        onBackPressed();
    }
    private void setPI(){
        TextView tv = (TextView) findViewById(viewID);
        switch (viewID){
            case R.id.infor_nome:
                pi.infor[0] = tv.getText().toString().intern();
                break;
            case R.id.infor_professor:
                pi.infor[2] = tv.getText().toString().intern();
                break;
            case R.id.infor_ven_mensalidade:
                pi.infor[3] = tv.getText().toString().intern();
                break;
            case R.id.infor_n_vez_semana:
                pi.infor[4] = tv.getText().toString().intern();
                break;
            case R.id.infor_imc:
                pi.infor[5] = tv.getText().toString().intern();
                break;
            case R.id.infor_date_init:
                String st = tv.getText().toString().intern();
                Mdate mdate = new Mdate(st);
                pi.infor[6] = new Gson().toJson(mdate);

                break;
            case R.id.infor_valid_ficha:
                pi.infor[7] = tv.getText().toString().intern();
                break;
            case R.id.infor_cantato:
                pi.infor[8] = tv.getText().toString().intern();
                break;
            case R.id.infor_cpf:
                pi.infor[9] = tv.getText().toString().intern();
                break;
        }
    }
    private void initComponents(){
        switch (pi.infor[1]){
            case "1":
                onCheckBox(checkBox1);
                break;
            case "2":
                onCheckBox(checkBox2);
                break;
            case "3":
                onCheckBox(checkBox3);
                break;
        }
        TextView tv;
            tv = findViewById(R.id.infor_nome);
            tv.setText(pi.infor[0]);
            tv = findViewById(R.id.infor_professor);
            tv.setText(pi.infor[2]);
            tv = findViewById(R.id.infor_ven_mensalidade);
            tv.setText(pi.infor[3]);
            tv = findViewById(R.id.infor_n_vez_semana);
            tv.setText(pi.infor[4]);
            tv = findViewById(R.id.infor_imc);
            tv.setText(pi.infor[5]);
            tv = findViewById(R.id.infor_date_init);
            tv.setText(new Gson().fromJson(pi.infor[6],Mdate.class).toString());
            tv = findViewById(R.id.infor_valid_ficha);
            tv.setText(pi.infor[7]);
            tv = findViewById(R.id.infor_cantato);
            tv.setText(pi.infor[8]);
            tv = findViewById(R.id.infor_cpf);
            tv.setText(pi.infor[9]);
        }

    public void onSave(View view) {
        pd.show();
        user.setPi(new Gson().toJson(pi));
        Main.db.logClass = new LogClass() {
            @Override
            public void onSucess() {
                pd.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure() {
                pd.dismiss();
                Toast.makeText(ActivityUserInfor.this, "erro au salvar!", Toast.LENGTH_SHORT).show();
            }
        };
        Main.db.update(user);

    }

    public void onCheckBox(View view) {
        int id = view.getId();
        switch (id){
            case R.id.checkbox_1:
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                pi.infor[1] = "1";
                break;
            case R.id.checkbox_2:
                checkBox2.setChecked(true);
                checkBox1.setChecked(false);
                checkBox3.setChecked(false);
                pi.infor[1] = "2";
                break;
            case R.id.checkbox_3:
                checkBox3.setChecked(true);
                checkBox2.setChecked(false);
                checkBox1.setChecked(false);
                pi.infor[1] = "3";
                break;
        }
    }
}