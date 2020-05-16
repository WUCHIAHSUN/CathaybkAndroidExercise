package com.example.cathaybkandroidexercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment implements CallbackListener, ApiCallback{
    private Context context;
    private RecyclerView recyclerView;
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

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ApiManager.getInstance().getUsersList(ApiManagerKey.GET_USERS_LIST, 20, this);

        return view;
    }

    @Override
    public void ItemDetail() {

    }

    @Override
    public void onResult(int tag, Data data) {
        if (data != null){
            switch (tag){
                case ApiManagerKey.GET_USERS_LIST:
                    UsersListData[] usersListData = (UsersListData[])data.getData();
                    userListSuccess(usersListData);
                    break;
            }
        }

    }

    private void userListSuccess(UsersListData[] usersListData){
        ListAdapter listAdapter = new ListAdapter(usersListData, this);
        recyclerView.setAdapter(listAdapter);
    }
}
