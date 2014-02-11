package com.rise;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.base.component.BaseActivity;
import com.rise.fragment.MainFragment;

public class MainActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment fragment = new MainFragment();
        fragmentTransaction.replace(R.id.replace_view,fragment);
//        fragmentTransaction.add(fragment, "main");
        fragmentTransaction.commit();
    }

}
