package com.francivaldo.drausioacademia.main.adapter;

import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.main.model.FichaTreino;

import java.util.List;

public class AdapterTreino extends RecyclerView.Adapter<AdapterTreino.ViewHolder> {
    private List<FichaTreino.Treino> userList;
    public AdapterTreino (List<FichaTreino.Treino>userList){
        this.userList = userList;
    }

    @NonNull
    @Override
    public AdapterTreino.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treino,parent,false);
        return new AdapterTreino.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTreino.ViewHolder holder, int position) {
        holder.setData(userList.get(position),position);
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public List<FichaTreino.Treino> getList() {
        return userList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, cmp1,cmp2,cmp3,cmp4;
        private EditText editText;
        Dialog dialog;
        int edtID;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_treino);
            cmp1 = itemView.findViewById(R.id.cmp1);
            cmp2 = itemView.findViewById(R.id.cmp2);
            cmp3 = itemView.findViewById(R.id.cmp3);
            cmp4 = itemView.findViewById(R.id.cmp4);
            cmp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickDialog(view);
                }
            });
            cmp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickDialog(view);
                }
            });
            cmp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickDialog(view);
                }
            });
            cmp3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickDialog(view);
                }
            });
            cmp4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickDialog(view);
                }
            });

            dialog = new Dialog(itemView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.edit_camp_treino);

            editText = dialog.findViewById(R.id.edt_text_treino);
            dialog.findViewById(R.id.confir_edt_treino).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv = (TextView) itemView.findViewById(edtID);
                    tv.setText(editText.getText());
                    int n;
                    try{
                        n = Integer.parseInt(tv.getText().toString());
                    }catch (Exception e){
                        n = 0;
                        tv.setText("0");
                        Log.d("AdapterTreino :","ERROR/parseInt");
                    }
                    switch (edtID){
                        case R.id.cmp1:
                            userList.get(position).cmp1 = n;
                            break;
                        case R.id.cmp2:
                            userList.get(position).cmp2 = n;
                            break;
                        case R.id.cmp3:
                            userList.get(position).cmp3 = n;
                            break;
                        case R.id.cmp4:
                            userList.get(position).cmp4 = n;
                            break;
                    }
                    userList.get(position).add = true;
                    editText.setText("");
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.cancel_edt_treino).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        public void onClickDialog(View view){
                edtID = view.getId();
                dialog.show();
        }
        public void setData(FichaTreino.Treino treino,int position){
            this.position = position;
            name.setText(treino.nameTreino);
            cmp1.setText(String.valueOf(treino.cmp1));
            cmp2.setText(String.valueOf(treino.cmp2));
            cmp3.setText(String.valueOf(treino.cmp3));
            cmp4.setText(String.valueOf(treino.cmp4));
        }
    }
}
