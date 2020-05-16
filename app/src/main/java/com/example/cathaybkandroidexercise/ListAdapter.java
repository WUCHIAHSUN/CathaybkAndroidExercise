package com.example.cathaybkandroidexercise;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter {

    private UsersListData[] usersListData;
    private CallbackListener callbackListener;
    Context context;
    private Utils utils;

    public ListAdapter(Context context, UsersListData[] usersListData, CallbackListener callbackListener){
        this.usersListData = usersListData;
        this.callbackListener = callbackListener;
        this.context = context;
        utils = Utils.getInstance();
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
        viewHolder.name.setText(usersListData[position].getLogin());
        Bitmap bitmap = utils.getBitmapMemory(usersListData[position].getAvatar_url());
        if (bitmap != null){
            viewHolder.imageView.setImageBitmap(bitmap);
        }else{
            utils.loadBitmap(viewHolder.imageView, usersListData[position].getAvatar_url());
        }
        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackListener.ItemDetail();
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersListData.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView imageView;
        private LinearLayout mainLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.main_layout);
            imageView = itemView.findViewById(R.id.title_icon);
            name = itemView.findViewById(R.id.name);
        }
    }
}
