package com.example.cathaybkandroidexercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainFragment extends Fragment implements CallbackListener, ApiCallback{
    private Context context;
    private RecyclerView recyclerView;
    private int since = 0;
    private ListAdapter listAdapter;
    private ArrayList<UsersListData> usersListDataArrayList;

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
        usersListDataArrayList = new ArrayList<>();
        setRecyclerView();
        ApiManager.getInstance().getUsersList(ApiManagerKey.GET_USERS_LIST, since, this);

        return view;
    }

    @Override
    public void ItemDetail(String name) {
        gotoNextPage(DetailFragment.getInstance(name));
    }

    @Override
    public void onResult(int tag, Data data) {
        if (data != null){
            switch (tag){
                case ApiManagerKey.GET_USERS_LIST:
                    UsersListData[] usersListData = (UsersListData[])data.getData();
                    userListSuccess(usersListData);
                    since = usersListData[usersListData.length -1].getId() + 1;
                    break;
            }
        }

    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && usersListDataArrayList != null && usersListDataArrayList.size() < 100) {
                    ApiManager.getInstance().getUsersList(ApiManagerKey.GET_USERS_LIST, since, MainFragment.this);
                }
            }
        });
    }

    private void userListSuccess(UsersListData[] usersListData){
        usersListDataArrayList.addAll(Arrays.asList(usersListData));
        if (listAdapter == null) {
            listAdapter = new ListAdapter(context, usersListDataArrayList, this);
            recyclerView.setAdapter(listAdapter);
        }else{
            listAdapter.notifyDataSetChanged();
        }

    }

    private void gotoNextPage(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.add(R.id.fragment, fragment).commit();
    }

}
