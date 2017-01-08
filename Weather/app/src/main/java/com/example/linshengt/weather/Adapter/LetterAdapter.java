package com.example.linshengt.weather.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.linshengt.weather.R;

import java.util.List;

/**
 * Created by 94437 on 2016/12/2.
 */
public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.BasicViewHolder>{
    private List<String> mList;
    public LetterAdapter(List list) {
        this.mList = list;
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewletter, parent, false);
        return new BasicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {
        holder.textView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class BasicViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public BasicViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   mLetterListener.onItemClick(getPosition());
                }
            });
        }
    }


    public void setmLetterListener(LetterListener mLetterListener) {
        this.mLetterListener = mLetterListener;
    }

    private LetterListener mLetterListener;
    public interface LetterListener{
        public void onItemClick(int position);
    }
}
