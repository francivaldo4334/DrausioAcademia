package com.francivaldo.drausioacademia.main.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.database.LogClass;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.model.Mdate;
import com.francivaldo.drausioacademia.main.model.User;

import java.util.List;

public class AdapterUsersMEs  extends RecyclerView.Adapter<AdapterUsersMEs.ViewHolder> {
    private List<User> userList;
    private int dateId;
    public AdapterUsersMEs (List<User>userList,int dateId){
        this.dateId = dateId;
        this.userList = userList;}
    @NonNull
    @Override
    public AdapterUsersMEs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_mes,parent,false);
        return new AdapterUsersMEs.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUsersMEs.ViewHolder holder, int position) {
        holder.setData(userList.get(position));
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameUser;
        private TextView dateInit;
        private TextView dateCheck;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameUser = itemView.findViewById(R.id.name_aluno_user_mes);
            dateInit = itemView.findViewById(R.id.date_aluno_init_user_mes);
            dateCheck = itemView.findViewById(R.id.date_aluno_check_user_mes);
            checkBox = itemView.findViewById(R.id.ic_checked_user_mes);
        }
        public void setData(User user){
            this.nameUser.setText(user.getName());
            Mdate today = user.getDate();
            String month = String.valueOf(today.month) ;
            month = today.month+1 < 10?"0"+month:month;
            String day = String.valueOf(today.day);
            day = today.day < 10?"0"+day:day;
            String date;
            date = day +"/"+ month +"/"+ today.year;
            this.dateInit.setText(date);
            this.dateCheck.setText(user.getMeses().mes[dateId].dateCheck);
            this.checkBox.setChecked(user.getMeses().mes[dateId].isCheck);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    if(checkBox.isChecked())
                        builder.setTitle(R.string.dialog_confirm_pag);
                    else
                        builder.setTitle(R.string.dialog_cancel_pag);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            user.getMeses().mes[dateId].isCheck = checkBox.isChecked();
                            ProgressDialog pd = new ProgressDialog(itemView.getContext());
                            pd.setTitle("gravando...");
                            pd.show();
                            Main.db.logClass = new LogClass() {
                                @Override
                                public void onSucess() {
                                    pd.dismiss();
                                    Toast.makeText(itemView.getContext(), "atualizado", Toast.LENGTH_SHORT).show();
                                    Main.db.logClass = null;
                                }

                                @Override
                                public void onFailure() {
                                    pd.dismiss();
                                    Toast.makeText(itemView.getContext(), "erro ou salvar!", Toast.LENGTH_SHORT).show();
                                }
                            };
                            Main.db.update(user);
                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checkBox.setChecked(!checkBox.isChecked());
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            });
        }
    }

}
