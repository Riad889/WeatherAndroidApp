package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private TextInputEditText et1;
    private Button b1;
    private TextView country;
    private TextView temp;
    private TextView condition;
    private ImageView img,backImg,searchIv;
    private RecyclerView rv;
   private ArrayList<WeatherRvModel>list;
    private WeatherRVAdapter adapter;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.editcityname);
        //b1=findViewById(R.id.search);
        backImg=findViewById(R.id.idIvBack);
        searchIv=findViewById(R.id.searchImageView);
        rv=findViewById(R.id.recylerView);
        country=findViewById(R.id.city_name);
        temp=findViewById(R.id.cityTemperature);
        condition=findViewById(R.id.ConditionText);
       img=findViewById(R.id.ConditionImage);
       list=new ArrayList<>();
      adapter= new WeatherRVAdapter(this,list);
       rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       searchIv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String ctname = et1.getText().toString();
               if (ctname.isEmpty()) {
                   Toast.makeText(MainActivity.this, "Enter the city name", Toast.LENGTH_SHORT).show();
               } else {
                   country.setText(ctname);
                   String url="https://api.weatherapi.com/v1/forecast.json?key=5378be30fc1d4c4bacc93018222501&q="+ctname+"&aqi=no";
                   RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
                   JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                       @Override
                       public void onResponse(JSONObject response) {
                           System.out.println(response);
                           list.clear();
                           String temperature= null;
                           try {
                               temperature = response.getJSONObject("current").getString("temp_c");
                               temp.setText(temperature+"°C");

                               String Condition=response.getJSONObject("current").getJSONObject("condition").getString("text");
                               String ConditionIcon=response.getJSONObject("current").getJSONObject("condition").getString("icon");

                               Picasso.get().load("http:"+ConditionIcon).into(img);
                               condition.setText(Condition);
                               int is_day=response.getJSONObject("current").getInt("is_day");
                               if(is_day==0)
                               {
                                   String Path="https://images.unsplash.com/photo-1539920951450-2b2d59cff66d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZGF5fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60";
                                   ///String Path="data:image/jpeg;base64,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";
                                    Picasso.get().load(Path).into(backImg);
                                   //Picasso.get().load("data:image/jpeg;base64,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)
                               }
                               else
                               {
                                    String Path2="https://images.pexels.com/photos/209831/pexels-photo-209831.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
                                    Picasso.get().load(Path2).into(backImg);
                               }





                               JSONObject forcast=response.getJSONObject("forecast");
                               JSONObject forcastDay=forcast.getJSONArray("forecastday").getJSONObject(0);
                               JSONArray forcastHour=forcastDay.getJSONArray("hour");
                               int i;
                               for(i=0;i<forcastHour.length();i++)
                               {
                                   JSONObject hourObj=forcastHour.getJSONObject(i);
                                   String time=hourObj.getString("time");
                                   String temper=hourObj.getString("temp_c");
                                   temper=temper+"°C";
                                   String img=hourObj.getJSONObject("condition").getString("icon");
                                   String wind=hourObj.getString("wind_kph");
                                   System.out.println(time);
                                   System.out.println(temper);
                                   System.out.println(img);
                                   System.out.println(wind);
                                   list.add(new WeatherRvModel(time,temper,img,wind));
                               }
                               adapter.notifyDataSetChanged();


                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Toast.makeText(MainActivity.this,"Enter the Valid City name",Toast.LENGTH_SHORT).show();

                       }
                   });
                   //getWeatherInfo(ctname);
                   rq.add(jor);
               }

           }
       });
    }
}
    /*public void getWeatherInfo(String Ctname){
        String cityName = Ctname;
        //System.out.println(cityName);
        String url="https://api.weatherapi.com/v1/current.json?key=5378be30fc1d4c4bacc93018222501&q="+cityName+"&aqi=no";
        RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jbo= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // list.clear();
                Log.d("response", String.valueOf(response));
                /*try {
                    String temperature=response.getJSONObject("current").getString("temp_c");
                    temp.setText(temperature+"°C");
                    int isDay=response.getJSONObject("current").getInt("is_day");
                    String Condition=response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String ConditionIcon=response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    System.out.println(ConditionIcon);
                    Picasso.get().load("http:"+ConditionIcon).into(img);
                    condition.setText(Condition);
                    /*
                    JSONObject forcast=response.getJSONObject("forecast");
                    JSONObject forcastDay=forcast.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray forcastHour=forcastDay.getJSONArray("hour");
                    int i;
                    for(i=0;i<forcastHour.length();i++)
                    {
                        JSONObject hourObj=forcastHour.getJSONObject(i);
                        String time=hourObj.getString("time");
                        String temper=hourObj.getString("temp_c");
                        String img=hourObj.getJSONObject("condition").getString("icon");
                        String wind=hourObj.getString("wind_kph");
                        System.out.println(time);
                        System.out.println(temper);
                        System.out.println(img);
                        System.out.println(wind);
                        list.add(new WeatherRvModel(time,temper,img,wind));
                    }
                    adapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this,"Enter the Valid City name",Toast.LENGTH_SHORT).show();
            }
        });


        */
