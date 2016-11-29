package com.example.linshengt.weather.Views.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linshengt.weather.Bean.Future24HWeatherBean;
import com.example.linshengt.weather.R;
import com.example.linshengt.weather.Utils.HLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 94437 on 2016/11/8.
 */
public class RecyclerViewHAdapter extends RecyclerView.Adapter<RecyclerViewHAdapter.MyHolder>{

    private String TAG = "RecyclerViewHAdapter";
    public RecyclerViewHAdapter(List<Future24HWeatherBean> list) {
        this.list = list;
    }

    private List<Future24HWeatherBean> list;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewitemh, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String weatherId;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd:HH");
        Date curData = new Date(System.currentTimeMillis());
        String str = formatter.format(curData);

        String a[] = str.split(":");
        String dd = a[0];
        String HH = a[1];
        if(dd.equals(list.get(position).getDate()) && HH.equals(list.get(position).getTime())){
            holder.tvTime.setText("现在");
        }else {
            holder.tvTime.setText(list.get(position).getTime()+":00");
        }

        holder.tvTemp.setText(list.get(position).getTemp()+"℃");
        int time = Integer.parseInt(list.get(position).getTime());

        weatherId = list.get(position).getWeather_id();
        switch (weatherId){
            case "00":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d00);else holder.imageView.setImageResource(R.drawable.n00);break;
            case "01":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d01);else holder.imageView.setImageResource(R.drawable.n01);break;
            case "02":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d02);else holder.imageView.setImageResource(R.drawable.n02);break;
            case "03":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d03);else holder.imageView.setImageResource(R.drawable.n03);break;
            case "04":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d04);else holder.imageView.setImageResource(R.drawable.n04);break;
            case "05":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d05);else holder.imageView.setImageResource(R.drawable.n05);break;
            case "06":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d06);else holder.imageView.setImageResource(R.drawable.n06);break;
            case "07":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d07);else holder.imageView.setImageResource(R.drawable.n07);break;
            case "08":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d08);else holder.imageView.setImageResource(R.drawable.n08);break;
            case "09":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d09);else holder.imageView.setImageResource(R.drawable.n09);break;
            case "10":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d10);else holder.imageView.setImageResource(R.drawable.n10);break;
            case "11":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d11);else holder.imageView.setImageResource(R.drawable.n11);break;
            case "12":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d12);else holder.imageView.setImageResource(R.drawable.n12);break;
            case "13":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d13);else holder.imageView.setImageResource(R.drawable.n13);break;
            case "14":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d14);else holder.imageView.setImageResource(R.drawable.n14);break;
            case "15":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d15);else holder.imageView.setImageResource(R.drawable.n15);break;
            case "16":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d16);else holder.imageView.setImageResource(R.drawable.n16);break;
            case "17":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d17);else holder.imageView.setImageResource(R.drawable.n17);break;
            case "18":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d18);else holder.imageView.setImageResource(R.drawable.n18);break;
            case "19":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d19);else holder.imageView.setImageResource(R.drawable.n19);break;
            case "20":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d20);else holder.imageView.setImageResource(R.drawable.n20);break;
            case "21":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d21);else holder.imageView.setImageResource(R.drawable.n21);break;
            case "22":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d22);else holder.imageView.setImageResource(R.drawable.n22);break;
            case "23":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d23);else holder.imageView.setImageResource(R.drawable.n23);break;
            case "24":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d24);else holder.imageView.setImageResource(R.drawable.n24);break;
            case "25":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d25);else holder.imageView.setImageResource(R.drawable.n25);break;
            case "26":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d26);else holder.imageView.setImageResource(R.drawable.n26);break;
            case "27":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d27);else holder.imageView.setImageResource(R.drawable.n27);break;
            case "28":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d28);else holder.imageView.setImageResource(R.drawable.n28);break;
            case "29":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d29);else holder.imageView.setImageResource(R.drawable.n29);break;
            case "30":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d30);else holder.imageView.setImageResource(R.drawable.n30);break;
            case "31":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d31);else holder.imageView.setImageResource(R.drawable.n31);break;
            case "53":if(time > 6 && time <= 18)holder.imageView.setImageResource(R.drawable.d53);else holder.imageView.setImageResource(R.drawable.n53);break;
            default:holder.imageView.setImageResource(R.drawable.d00);break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private TextView tvTime, tvTemp;
        private ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tvTiem);
            tvTemp = (TextView) itemView.findViewById(R.id.tvTemperature);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
