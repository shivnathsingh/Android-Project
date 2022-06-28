package com.example.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class order_food extends AppCompatActivity {
    RecyclerView rcView;
    private static final String TAG = "order_food";
    private ArrayList<DataModal> allData= new ArrayList<>();
    Restro_Adapter adapter;
    //FirebaseRecyclerOptions<DataModal> options;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);
        rcView=findViewById(R.id.recyclerView);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        dataEntry();

        FirebaseDatabase.getInstance().getReference().child("restaurent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DataModal dataModal = dataSnapshot.getValue(DataModal.class);
                    dataModal.ID = dataSnapshot.getKey();
                    allData.add(dataModal);
                }
                adapter=new Restro_Adapter(order_food.this,allData);
                rcView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.layout.menu,menu);

        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    List<DataModal> filteredList=allData.stream().filter(data->data.name.toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
                    adapter.updateList(filteredList);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }


    public void dataEntry(){
        OkHttpClient client = new OkHttpClient();

        String url = "https://travel-advisor.p.rapidapi.com/restaurants/list-by-latlng?latitude=28.543680&longitude=77.198692&limit=15&currency=USD&distance=2&open_now=false&lunit=km&lang=en_U";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Host", "travel-advisor.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "9a8a6b8942msh7862e75a384b437p113bfajsn9368eb03cb21")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
//                    Log.i("first char",""+myResponse.charAt(0));
//                    Log.i("last char",""+myResponse.charAt(myResponse.length()-1));
                    //Log.i("data",myResponse);
                    JSONObject jsnObject= null;
                    JSONObject subJsnObject= null;
                    try {
                        jsnObject = new JSONObject(myResponse);
                        Log.i("response",""+myResponse);
                        JSONArray data=jsnObject.getJSONArray("data");
                        int len=data.length();
                        int number_of_rest = 0;
                        int data_idx =0;
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("restaurent");
                        while(number_of_rest < 5 ){
                            // here we will select the res who has the photos only
                            subJsnObject = new JSONObject(""+data.get(data_idx));
                            if (subJsnObject.has("photo")) {

                                number_of_rest++;
                                ref.child(""+ number_of_rest).child("address").setValue(subJsnObject.getString("address"));
                                ref.child(""+ number_of_rest).child("name").setValue(subJsnObject.getString("name"));
                                double dist=Double.parseDouble(subJsnObject.getString("distance"));
                                dist = (dist/40)*60; // for time in minutes
                                ref.child(""+ number_of_rest).child("time").setValue("" + dist);
                                ref.child(""+ number_of_rest).child("rating").setValue(subJsnObject.getString("rating"));
                                /* photo to url logic */
                                 String myurl= new JSONObject(new JSONObject(new JSONObject(subJsnObject.getString("photo")).getString("images")).getString("original"))
                                        .getString("url");

                                ref.child(""+ number_of_rest).child("url").setValue(myurl);
                                // done
                                Log.i("nor",""+number_of_rest);
                             }
                            data_idx++;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

}
