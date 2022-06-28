package com.example.food;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.MeraViewHolder> {
    static int curr;
    OrderModal temp1;
    OrderModal temp2;
    OrderModal temp;
    Context content;
    public static Map<String, OrderModal> map = new HashMap();
    ArrayList<MenuModal> model;
    private MeraViewHolder mHolder;

    public Adapter2(Context context, ArrayList<MenuModal> options) {
        this.content = context;
        this.model = options;
        //Toast.makeText(context, "length "+options.size() ,Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public MeraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Toast.makeText(content, "On create in adapter ", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_details, parent, false);
        return new MeraViewHolder(view);
    }
//holder.getAdapterPosition()
    @Override
    public void onBindViewHolder(@NonNull MeraViewHolder holder, @SuppressLint("RecyclerView") int position)
    {//mHolder = holder;

        holder.menu_name.setText(model.get(position).getName());
        holder.menu_description.setText(model.get(position).getDescription());
        holder.menu_price.setText("price - "+model.get(position).getPrice()+"Rs");
        // holder.menu_discount.setText(model.getDiscount());
        //Glide.with(holder.menu_image.getContext()).load(model.getUrl()).into(holder.menu_image);
        Glide.with(holder.menu_image.getContext()).load(model.get(position).getUrl()).into(holder.menu_image);
        String key = "" + position;


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (key.equals("1"))
                    temp = temp1;
                else
                    temp = temp2;
                if (map.containsKey(key)) {
                    curr = temp.getQuant() + 1;
                    holder.quant.setText("" + curr);
                    temp.setQuant(curr);
                    temp.setTotalPrice(curr * Integer.parseInt(model.get(position).getPrice()));
                    map.put(key, temp);
                    Log.i("key", key);
                    if (key.equals("1"))
                        temp1 = temp;
                    else
                        temp2 = temp;

                } else {
                    curr = 1;
                    holder.quant.setText("" + curr);
                    temp = new OrderModal(model.get(position).getName(), model.get(position).url, curr, curr * Integer.parseInt(model.get(position).getPrice()));
                    map.put(key, temp);
                    Log.i("key", key);
                    if (key.equals("1"))
                        temp1 = temp;
                    else
                        temp2 = temp;
                }
                Log.i("map-->", "" + curr + " " + curr * Integer.parseInt(model.get(position).getPrice()));
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (key.equals("1"))
                    temp = temp1;
                else
                    temp = temp2;
                if (map.containsKey(key)) {
                    curr = temp.getQuant() - 1;
                    if (curr < 0)
                        curr = 0;
                    holder.quant.setText("" + curr);
                    temp.setQuant(curr);
                    temp.setTotalPrice(curr * Integer.parseInt(model.get(position).getPrice()));
                    map.put(key, temp);
                    Log.i("key", key);
                    if (key.equals("1"))
                        temp1 = temp;
                    else
                        temp2 = temp;
                } else {
                    curr = 0;
                    holder.quant.setText("" + curr);
                    temp = new OrderModal(model.get(position).getName(), model.get(position).url, curr, curr * Integer.parseInt(model.get(position).getPrice()));
                    map.put(key, temp);
                    Log.i("key", key);
                    if (key.equals("1"))
                        temp1 = temp;
                    else
                        temp2 = temp;
                }
                Log.i("map-->", "" + curr + " " + curr * Integer.parseInt(model.get(position).getPrice()));
            }
        });

    }


    @Override
    public int getItemCount() {
        //Toast.makeText(content, "Get Item count called", Toast.LENGTH_SHORT).show();
        return model.size();
    }

    public static class MeraViewHolder extends RecyclerView.ViewHolder {
        TextView menu_name,menu_description,menu_price,quant;
        ImageView menu_image;
        Button add,remove;
        public MeraViewHolder(@NonNull View itemView) {
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