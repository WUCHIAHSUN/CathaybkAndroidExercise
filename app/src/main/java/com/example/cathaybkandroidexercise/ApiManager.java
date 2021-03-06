package com.example.cathaybkandroidexercise;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiManager {

    private OkHttpClient client = new OkHttpClient();
    private int mTag;
    private String Url = "";
    private ApiCallback apiCallback;

    public static ApiManager getInstance(){
        return new ApiManager();
    }

    public void getUsersList(int tag, int since, ApiCallback apiCallback){
        Url = "https://api.github.com/users?since=" + since;
        mTag = tag;
        this.apiCallback = apiCallback;
        new ApiTask().execute(Url);
    }

    public void getSingleUser(int tag, String name, ApiCallback apiCallback){
        Url = "https://api.github.com/users/" + name;
        mTag = tag;
        this.apiCallback = apiCallback;
        new ApiTask().execute(Url);
    }

    private class ApiTask extends AsyncTask<String , Integer , Data> {

        @SuppressLint("NewApi")
        @Override
        protected Data doInBackground(String... url) {
            try {
                return Run(Url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Data data) {
            //執行後 完成背景任務
            super.onPostExecute(data);
            apiCallback.onResult(mTag, data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Data Run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return getJson(response.body().string());
        }
    }

    private Data getJson(String json){
        Log.d("Json: ", json);
        Gson gson = new Gson();
        Object jsonString;
        String message = null;
        try {
            JSONObject jresponse = new JSONObject(json);
            message = jresponse.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (message != null) {
            return null;
        }
        switch (mTag){
            case ApiManagerKey.GET_USERS_LIST:
                jsonString = gson.fromJson(json, UsersListData[].class);
                break;
            case ApiManagerKey.GET_USER_DETAIL:
                jsonString = gson.fromJson(json, UsersListData.class);
                break;
            default:
                jsonString = null;
                break;
        }

        Data data = new Data();
        data.setData(jsonString);
        return data;
    };
}
