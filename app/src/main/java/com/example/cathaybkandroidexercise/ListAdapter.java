package com.example.cathaybkandroidexercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter {

    private ArrayList<String> titleList = new ArrayList<>();
    private CallbackListener callbackListener;

    public ListAdapter(ArrayList<String> titleList, CallbackListener callbackListener){
        this.titleList.addAll(titleList);
        this.callbackListener = callbackListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        String title = titleList.get(position);
        viewHolder.tvTitle.setText(title);

        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackListener.ItemDetail();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView imageView;
        private RelativeLayout mainLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.main_layout);
            imageView = itemView.findViewById(R.id.title_icon);
            tvTitle = itemView.findViewById(R.id.name);
        }
    }
}
