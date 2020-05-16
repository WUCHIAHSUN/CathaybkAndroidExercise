package com.example.cathaybkandroidexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment(this);
        gotoNextPage(mainFragment);

    }

    private void gotoNextPage(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }
}
