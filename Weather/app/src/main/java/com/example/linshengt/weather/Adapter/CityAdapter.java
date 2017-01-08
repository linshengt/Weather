package com.example.linshengt.weather.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.linshengt.weather.R;
import com.example.linshengt.weather.Utils.HLog;

import java.util.List;

/**
 * Created by 94437 on 2016/12/2.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.BasicViewHoldr>{

    private String TAG = "CityAdapter";
    private List<String> mListCity;
    private List<String> mListLetter;
    final static int TYPE_1=1;
    final static int TYPE_2=2;


    public CityAdapter(List<String> mListCity, List<String> mListLetter) {
        this.mListCity = mListCity;
        this.mListLetter = mListLetter;
    }

    @Override
    public BasicViewHoldr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewcity, parent, false);
        return new BasicViewHoldr(view);
    }

    @Override
    public void onBindViewHolder(BasicViewHoldr holder, int position) {
        holder.textView.setText(mListCity.get(position));
        if(getViewType(position) == TYPE_1){
            holder.rl.setBackgroundColor(Color.argb(0xff, 0xde,0xdc,0xde));
        }else {
            holder.rl.setBackgroundColor(Color.argb(0xff, 0xff,0xff,0xff));
        }
    }


    public int getViewType(int position) {
        for(int i=0;i<mListLetter.size();i++){
            if(mListCity.get(position).equals(mListLetter.get(i))){
                return TYPE_1;
            }
        }
        return TYPE_2;
    }

    @Override
    public int getItemCount() {
        return mListCity.size();
    }

    class BasicViewHoldr extends RecyclerView.ViewHolder{
        private TextView textView;
        private RelativeLayout rl;
        public BasicViewHoldr(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvItem);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCityRecyclerListener!=null){
                        if(getViewType(getPosition())==TYPE_2){
                            rl.setBackgroundColor(Color.argb(0xff,0xd1,0xc7,0xc7));//d1c7c7
                            mCityRecyclerListener.onItemClick(getPosition());
                        }
                    }
                }
            });
        }
    }

    public void setmCityRecyclerListener(CityRecyclerListener mCityRecyclerListener) {
        this.mCityRecyclerListener = mCityRecyclerListener;
    }

    private  CityRecyclerListener mCityRecyclerListener;
    public interface CityRecyclerListener{
        public void onItemClick(int position);
    }
}
