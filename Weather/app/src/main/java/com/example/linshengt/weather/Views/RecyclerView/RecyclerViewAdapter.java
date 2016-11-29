package com.example.linshengt.weather.Views.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linshengt.weather.Bean.FutureWeatherBean;
import com.example.linshengt.weather.R;
import com.example.linshengt.weather.Utils.HLog;
import com.example.linshengt.weather.Views.histogram;

import java.util.List;

/**
 * Created by 94437 on 2016/11/7.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyRecyclerViewHolder>{

    private String TAG = "RecyclerViewAdapter";
    public RecyclerViewAdapter(List<FutureWeatherBean> list) {
        this.list = list;
    }

    private List<FutureWeatherBean> list;

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewitem, parent, false);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        String highTemp="", lowTemp="";
        String str = list.get(position).getTemp();
        String strTemp;

        for (int i = 0; i < str.length(); i ++){
            if(str.substring(i, i+1).equals("℃")){
                lowTemp = str.substring(0, i);
                strTemp = str.substring(i, str.length());
                int k = 0;
                for (int j = 0; j < strTemp.length(); j ++){
                    if(strTemp.substring(j, j+1).equals("~")){
                        k = j;
                    }
                    if (k!=0 && strTemp.substring(j, j+1).equals("℃")){
                        highTemp = strTemp.substring(k+1, j);
                        break;
                    }
                }
                break;
            }
        }
        holder.textView.setText(list.get(position).getWeekk());
        HLog.i(TAG, "lowTemp:" + lowTemp + "   highTemp:" + highTemp);
        holder.histogram.setTemp(Integer.parseInt(highTemp), Integer.parseInt(lowTemp));

        String weatherIdFa = list.get(position).getWeather_id_fa();
        String weatherIdFb = list.get(position).getWeather_id_fb();
        switch (weatherIdFa){
            case "00":holder.imageView.setImageResource(R.drawable.d00);break;
            case "01":holder.imageView.setImageResource(R.drawable.d01);break;
            case "02":holder.imageView.setImageResource(R.drawable.d02);break;
            case "03":holder.imageView.setImageResource(R.drawable.d03);break;
            case "04":holder.imageView.setImageResource(R.drawable.d04);break;
            case "05":holder.imageView.setImageResource(R.drawable.d05);break;
            case "06":holder.imageView.setImageResource(R.drawable.d06);break;
            case "07":holder.imageView.setImageResource(R.drawable.d07);break;
            case "08":holder.imageView.setImageResource(R.drawable.d08);break;
            case "09":holder.imageView.setImageResource(R.drawable.d09);break;
            case "10":holder.imageView.setImageResource(R.drawable.d10);break;
            case "11":holder.imageView.setImageResource(R.drawable.d11);break;
            case "12":holder.imageView.setImageResource(R.drawable.d12);break;
            case "13":holder.imageView.setImageResource(R.drawable.d13);break;
            case "14":holder.imageView.setImageResource(R.drawable.d14);break;
            case "15":holder.imageView.setImageResource(R.drawable.d15);break;
            case "16":holder.imageView.setImageResource(R.drawable.d16);break;
            case "17":holder.imageView.setImageResource(R.drawable.d17);break;
            case "18":holder.imageView.setImageResource(R.drawable.d18);break;
            case "19":holder.imageView.setImageResource(R.drawable.d19);break;
            case "20":holder.imageView.setImageResource(R.drawable.d20);break;
            case "21":holder.imageView.setImageResource(R.drawable.d21);break;
            case "22":holder.imageView.setImageResource(R.drawable.d22);break;
            case "23":holder.imageView.setImageResource(R.drawable.d23);break;
            case "24":holder.imageView.setImageResource(R.drawable.d24);break;
            case "25":holder.imageView.setImageResource(R.drawable.d25);break;
            case "26":holder.imageView.setImageResource(R.drawable.d26);break;
            case "27":holder.imageView.setImageResource(R.drawable.d27);break;
            case "28":holder.imageView.setImageResource(R.drawable.d28);break;
            case "29":holder.imageView.setImageResource(R.drawable.d29);break;
            case "30":holder.imageView.setImageResource(R.drawable.d30);break;
            case "31":holder.imageView.setImageResource(R.drawable.d31);break;
            case "53":holder.imageView.setImageResource(R.drawable.d53);break;
            default:holder.imageView.setImageResource(R.drawable.d00);break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private histogram  histogram;
        private ImageView imageView;
        public MyRecyclerViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.itemWeek);
            histogram = (com.example.linshengt.weather.Views.histogram) itemView.findViewById(R.id.histogram);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
