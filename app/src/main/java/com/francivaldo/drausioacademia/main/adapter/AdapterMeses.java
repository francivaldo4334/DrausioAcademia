package com.francivaldo.drausioacademia.main.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.main.model.Meses;
import com.francivaldo.drausioacademia.mensalidades.Mes;

import java.util.List;

public class AdapterMeses extends RecyclerView.Adapter<AdapterMeses.ViewHolder> {
    private List<Meses.Mes> itemMes;
    public AdapterMeses (List<Meses.Mes>userList){this.itemMes = userList;}
    @NonNull
    @Override
    public AdapterMeses.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mes,parent,false);
        return new AdapterMeses.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterMeses.ViewHolder holder, int position) {
        String name = itemMes.get(position).text;
        boolean isCheck = itemMes.get(position).isCheck;
        holder.setData(name,isCheck);
    }
    @Override
    public int getItemCount() {
        return itemMes.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mes;
        private ImageView ic_check;
        private RelativeLayout user_lay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mes = itemView.findViewById(R.id.name_mes);
            ic_check = itemView.findViewById(R.id.ic_checked);
            user_lay = itemView.findViewById(R.id.lay_mes);
        }
        public void setData(String name,boolean isCheck){
            this.mes.setText(name);
            this.ic_check.setSelected(isCheck);
            user_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), Mes.class);
                    intent.putExtra("NAME",name);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
