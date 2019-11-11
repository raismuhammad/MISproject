package com.example.restexample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restexample.Adapter.RecyclerViewAdapter;
import com.example.restexample.HTTP.API;
import com.example.restexample.HTTP.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Service service;
    ImageButton imgbtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        imgbtnBack = findViewById(R.id.imgBtnBack);
        service = API.getClient().create(Service.class);

        imgbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Call<JsonObject> getOffice = service.getOffice();
        getOffice.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("var_result").getAsString().equals("1")) {
                        JsonObject object = response.body();

                        JsonArray array = object.get("result").getAsJsonArray();
                        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                        for (int i = 0; i < array.size(); i++) {
                            JsonObject mObject = array.get(i).getAsJsonObject();
                            HashMap<String, String> item = new HashMap<>();
                            item.put("office_name", mObject.get("office_name").getAsString());
                            item.put("office_address", mObject.get("office_address").getAsString());
                            item.put("office_description", mObject.get("office_description").getAsString());
                            item.put("cell_phone", mObject.get("cell_phone").getAsString());
                            item.put("email", mObject.get("email").getAsString());
                            item.put("location_gps", mObject.get("location_gps").getAsString());
                            item.put("base_url", mObject.get("base_url").getAsString());
                            arrayList.add(item);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(new RecyclerViewAdapter(MainActivity.this, arrayList));
                    } else {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
