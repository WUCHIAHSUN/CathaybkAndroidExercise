package com.example.cathaybkandroidexercise;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment implements ApiCallback{
    private ImageView imageView, userPic;
    private TextView userName, bio, login, location, link, staff;
    private String do_data = "<No data>";

    public static DetailFragment getInstance(String name){
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setData(name);
        return detailFragment;
    }

    private void setData(String name){
        ApiManager.getInstance().getSingleUser(ApiManagerKey.GET_USER_DETAIL, name, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.detail_layout, container, false);
        imageView = view.findViewById(R.id.ic_close);
        userPic = view.findViewById(R.id.user_pic);
        userName = view.findViewById(R.id.user_name);
        staff = view.findViewById(R.id.staff);
        bio = view.findViewById(R.id.bio);
        login = view.findViewById(R.id.login);
        location = view.findViewById(R.id.location);
        link = view.findViewById(R.id.blog);
        imageView.setOnClickListener(onClickListener);
        link.setOnClickListener(onClickListener);

        return view;
    }

    private String setDataText(String text){
        if (text == null || text.isEmpty()){
            return do_data;
        }
        return text;
    }

    private String setLinkText(TextView textView, String text){
        if (text == null || text.isEmpty()){
            return do_data;
        }
        if (getActivity() != null) {
            textView.setTextColor(getActivity().getResources().getColor(R.color.link_blue));
        }
        return text;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ic_close:
                    getFragmentManager().popBackStack();
                    break;
                case R.id.blog:
                    if (!link.getText().equals(do_data)) {
                        web(link.getText().toString());
                    }
                    break;
            }
        }
    };

    @Override
    public void onResult(int tag, Data data) {
        if (data != null) {
            switch (tag) {
                case ApiManagerKey.GET_USER_DETAIL:
                    UsersListData usersListData = (UsersListData) data.getData();
                    detailSuccess(usersListData);
                    break;
            }
        }else{
            if (getContext() != null) {
                Toast.makeText(getContext(), "操作太頻繁", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void detailSuccess(UsersListData usersListData){
        Picasso.with(userPic.getContext()).cancelRequest(userPic);
        Picasso.with(userPic.getContext()).load(usersListData.getAvatar_url()).transform(new CircleTransform()).into(userPic);

        userName.setText(setDataText(usersListData.getName()));
        if (usersListData.isSiteAdmin()){
            staff.setVisibility(View.VISIBLE);
        }else{
            staff.setVisibility(View.GONE);
        }
        if (usersListData.getBio() != null) {
            bio.setText(Html.fromHtml(usersListData.getBio()));
        }
        login.setText(setDataText(usersListData.getLogin()));
        location.setText(setDataText(usersListData.getLocation()));
        link.setText(setLinkText(link, usersListData.getBlog()));
    }

    private void web(String uri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}
