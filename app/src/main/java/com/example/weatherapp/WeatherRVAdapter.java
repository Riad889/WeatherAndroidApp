package com.example.weatherapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WeatherRvModel>wrm;

    public WeatherRVAdapter(Context context, ArrayList<WeatherRvModel> wrm) {
        this.context = context;
        this.wrm = wrm;
    }

    @NonNull
    @Override

    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.weather_rv,parent,false);
       return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRVAdapter.ViewHolder holder, int position) {
        WeatherRvModel modal=wrm.get(position);
        holder.rvTemp.setText(modal.getTemp());

        Picasso.get().load("http:".concat(modal.getIcon())).into(holder.rvConditionImg);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output=new SimpleDateFormat("hh:mm aa");
        try{
            Date t=sdf.parse(modal.getTime());
            holder.rvTime.setText(output.format(t));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return wrm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rvTime;
        private TextView rvCondition;
        private TextView rvTemp;
        private ImageView rvConditionImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvTime=itemView.findViewById(R.id.rvtime);
            rvCondition=itemView.findViewById(R.id.rvCondition);
            rvTemp=itemView.findViewById(R.id.rvtemp);
            rvConditionImg=itemView.findViewById(R.id.rvconditionImg);
        }
    }
}

