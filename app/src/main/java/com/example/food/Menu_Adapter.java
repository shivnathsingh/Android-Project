package com.example.food;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Menu_Adapter extends FirebaseRecyclerAdapter<MenuModal,Menu_Adapter.MeraViewHolder> {
    static int curr;
    OrderModal temp1;
    OrderModal temp2;
    OrderModal temp;
    public static Map<String,OrderModal> map=new HashMap();
    public Menu_Adapter(@NonNull FirebaseRecyclerOptions<MenuModal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Menu_Adapter.MeraViewHolder holder, int position, @NonNull MenuModal model) {
        holder.menu_name.setText(model.getName());
        holder.menu_description.setText(model.getDescription());
        holder.menu_price.setText("price - "+model.getPrice()+"Rs");
       // holder.menu_discount.setText(model.getDiscount());
        Glide.with(holder.menu_image.getContext()).load(model.getUrl()).into(holder.menu_image);
        String key=getRef(position).getKey();

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(key.equals("1"))
                    temp=temp1;
                else
                    temp=temp2;
                if(map.containsKey(key)){
                    curr=temp.getQuant()+1;
                    holder.quant.setText(""+curr);
                    temp.setQuant(curr);
                    temp.setTotalPrice(curr * Integer.parseInt(model.getPrice()));
                    map.put(key,temp);
                    Log.i("key",key);
                    if(key.equals("1"))
                        temp1=temp;
                    else
                        temp2=temp;

                }
                else{
                    curr=1;
                    holder.quant.setText(""+curr);
                  temp=new OrderModal(model.getName(),model.url,curr,curr * Integer.parseInt(model.getPrice()));
                  map.put(key,temp);
                    Log.i("key",key);
                    if(key.equals("1"))
                        temp1=temp;
                    else
                        temp2=temp;
                }
               Log.i("map-->",""+curr+" "+curr * Integer.parseInt(model.getPrice()));
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(key.equals("1"))
                    temp=temp1;
                else
                    temp=temp2;
                if(map.containsKey(key)){
                    curr= temp.getQuant()-1;
                    if(curr<0)
                        curr=0;
                    holder.quant.setText(""+curr);
                    temp.setQuant(curr);
                    temp.setTotalPrice(curr * Integer.parseInt(model.getPrice()));
                    map.put(key, temp);
                    Log.i("key",key);
                    if(key.equals("1"))
                        temp1=temp;
                    else
                        temp2=temp;
                }
                else{
                    curr=0;
                    holder.quant.setText(""+curr);
                    temp=new OrderModal(model.getName(),model.url,curr,curr * Integer.parseInt(model.getPrice()));
                    map.put(key,temp);
                    Log.i("key",key);
                    if(key.equals("1"))
                        temp1=temp;
                    else
                        temp2=temp;
                }
                Log.i("map-->",""+curr+" "+curr * Integer.parseInt(model.getPrice()));
            }
        });

    }

    @Override
    public Menu_Adapter.MeraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_details,parent,false);

        return new Menu_Adapter.MeraViewHolder(view);
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
