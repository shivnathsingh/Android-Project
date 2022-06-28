package com.example.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Apater3 extends RecyclerView.Adapter<Apater3.Holder> {
Context context;
    ArrayList<MenuModal> lst;
    public Apater3(Context context, ArrayList<MenuModal> lst) {
        this.context = context;
        this.lst = lst;
        Toast.makeText(context, "Length "+lst.size(), Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Toast.makeText(context, "Oncreate called ", Toast.LENGTH_LONG).show();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_details,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Toast.makeText(context, "oN  binder call",Toast.LENGTH_LONG).show();
        String name1= lst.get(position).name;
        String dis1= lst.get(position).discount;
        String price1= lst.get(position).price;
        Toast.makeText(context, "N "+name1+" dis1 "+dis1+"  price1 "+price1,Toast.LENGTH_LONG).show();
        holder.menu_name.setText(name1);
        holder.menu_price.setText(price1);

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class  Holder extends RecyclerView.ViewHolder {

        TextView menu_name,menu_description,menu_price,quant;
        ImageView menu_image;
        Button add,remove;
        public Holder(@NonNull View itemView) {
            super(itemView);

            menu_name=itemView.findViewById(R.id.menu_name);
            menu_description=itemView.findViewById(R.id.menu_description);
            //menu_discount=itemView.findViewById(R.id.menu_discount);
            menu_price=itemView.findViewById(R.id.menu_price);
            menu_image=itemView.findViewById(R.id.menu_image);
            add=itemView.findViewById(R.id.add);
            remove=itemView.findViewById(R.id.remove);
            quant=itemView.findViewById(R.id.quantView);
        }
    }


}
