package com.francivaldo.drausioacademia.main.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.aluno.AlunoInfor;
import com.francivaldo.drausioacademia.aluno.Alunos;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.model.Mdate;
import com.francivaldo.drausioacademia.main.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdapterAlunos extends RecyclerView.Adapter<AdapterAlunos.ViewHolder> {
    private List<User> userList;
    Alunos alunos;
    public AdapterAlunos(Alunos alunos, List<User> userList){
        this.alunos = alunos;
        this.userList = userList;}
    @NonNull
    @Override
    public AdapterAlunos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno,parent,false);
        return new AdapterAlunos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAlunos.ViewHolder holder, int position) {
        holder.setData(userList.get(position));
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<User> filterList) {
        userList = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView date;
        private RelativeLayout user_lay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_aluno);
            date = itemView.findViewById(R.id.date_aluno);
            user_lay = itemView.findViewById(R.id.user_lay);
        }
        public void setData(User user){
            this.name.setText(user.getName());
            Mdate today = user.getDate();
            String month = String.valueOf(today.month) ;
            month = today.month+1 < 10?"0"+month:month;
            String day = String.valueOf(today.day);
            day = today.day < 10?"0"+day:day;
            String date;
            date = day +"/"+ month +"/"+ today.year;
            this.date.setText(date);
            user_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), AlunoInfor.class);
                    intent.putExtra(Main.db.ID,user.getId());
                    intent.putExtra(Main.db.INFOR,user.getPIString());
                    intent.putExtra(Main.db.TREINO,user.getTreinoString());
                    intent.putExtra(Main.db.MESES,user.getMesesString());
                    itemView.getContext().startActivity(intent);
                    alunos.finish();
                }
            });
        }
    }

}
