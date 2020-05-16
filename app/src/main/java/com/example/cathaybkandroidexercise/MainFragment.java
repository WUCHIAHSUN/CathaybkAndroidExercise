package com.example.cathaybkandroidexercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment implements CallbackListener{
    private Context context;
    public MainFragment(Context context){
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        RecyclerView relativeLayout = view.findViewById(R.id.list);
        relativeLayout.setLayoutManager(new LinearLayoutManager(context));
//        ListAdapter listAdapter = new ListAdapter();
//        relativeLayout.setAdapter(listAdapter);

        return view;
    }

    @Override
    public void ItemDetail() {

    }
}
