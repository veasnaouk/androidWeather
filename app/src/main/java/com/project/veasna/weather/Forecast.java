package com.project.veasna.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by Veasna on 6/25/2017.
 */

public class Forecast extends Fragment{
    private static RequestQueue queue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        queue=Volley.newRequestQueue(getContext());
        final View content=inflater.inflate(R.layout.content_forecast, container, false);

        final TextView tvCity=(TextView)content.findViewById(R.id.tvCity) ;
        final RecyclerView rv=(RecyclerView)content.findViewById(R.id.rclView);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Common.getForecastURL("London"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject contentJSON=new JSONObject(response);
                            JSONObject city=contentJSON.getJSONObject("city");


                            tvCity.setText("Weekly forecast for "+city.getString("name")+", "+city.getString("country"));
                            JSONArray array=contentJSON.getJSONArray("list");

                            Adapter adp = new Adapter(array);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            rv.setLayoutManager(mLayoutManager);
                            rv.setItemAnimator(new DefaultItemAnimator());
                            rv.setAdapter(adp);
                        }catch (Exception e){
                            Log.e("Error",e.getMessage()+"");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse",error.getMessage());
            }
        });
        queue.add(stringRequest);
        return content;
    }

    static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
        private JSONArray array;

        public Adapter(JSONArray array) {
            this.array = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.forecast_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {
                holder.setData(array.getJSONObject(position));
            }catch (Exception e){}

        }

        @Override
        public int getItemCount() {
            return array.length();
        }

        static class ViewHolder extends RecyclerView.ViewHolder{
            private TextView tvDate;
            private ImageView ivWeather;
            private TextView tvMin;
            private TextView tvMax;

            public ViewHolder(View itemView) {
                super(itemView);
                tvDate=(TextView)itemView.findViewById(R.id.date);
                tvMin=(TextView)itemView.findViewById(R.id.min);
                tvMax=(TextView)itemView.findViewById(R.id.max);
                ivWeather=(ImageView) itemView.findViewById(R.id.ivWeather);

            }
            public void setData(JSONObject ob){
                try {
                    tvDate.setText(Common.epochToDayOfTheWeek(ob.getLong("dt")));
                    makeImageRequest(ivWeather,Common.getIconURL(ob.getJSONArray("weather").getJSONObject(0).getString("icon")));
                    tvMin.setText(Common.kelvinToCelsius(ob.getJSONObject("temp").getDouble("min"))+"C");
                    tvMax.setText(Common.kelvinToCelsius(ob.getJSONObject("temp").getDouble("max"))+"C");
                }catch (Exception e){

                }

            }
            private void makeImageRequest(final ImageView iv,String imageName){
                ImageRequest imageRequest=new ImageRequest(imageName, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv.setImageBitmap(response);
                    }
                }, 0, 0, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(imageRequest);
            }
        }
    }

}
