package com.rise;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rise.common.Const;
import com.rise.component.BaseActivity;
import com.rise.component.Sign;

/**
 * Created by kai.wang on 5/16/14.
 */
public class SignInActivity extends BaseActivity {
    private EditText nameView,passView;
    private Button signInBtn;

    private String name;
    private String pass;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Const.ACTION_SIGN_FAIL.equals(action)) {
                Toast.makeText(SignInActivity.this, R.string.sign_in_fail, Toast.LENGTH_SHORT).show();
                enableUi();
            } else if (Const.ACTION_SIGN_SUCCESS.equals(action)) {
                Const.USER_ID = intent.getIntExtra("user_id", -1);
                Toast.makeText(SignInActivity.this, R.string.sign_in_success, Toast.LENGTH_SHORT).show();
                SignInActivity.this.finish();
                SharedPreferences preferences = getSharedPreferences(Const.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(Const.SHARED_FILED_USER_ID, Const.USER_ID);
                editor.commit();
                startService(new Intent(SignInActivity.this,SyncService.class));
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        setContentView(R.layout.activity_sign_in);

        initView();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.ACTION_SIGN_FAIL);
        filter.addAction(Const.ACTION_SIGN_SUCCESS);
        registerReceiver(receiver,filter);

    }

    private void initView(){
        nameView = (EditText) findViewById(R.id.sign_in_name).findViewById(R.id.sign_text);
        passView = (EditText) findViewById(R.id.sign_in_pass).findViewById(R.id.sign_text);
        signInBtn = (Button) findViewById(R.id.sign_btn);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.sign_btn:
                if(check()){
                    disableUi();
                    Sign.in(SignInActivity.this,name,pass);
                }else{
                    return;
                }
                break;
        }
    }

    public boolean check(){
        name = nameView.getText().toString();
        pass = passView.getText().toString();

        // check username
        if(TextUtils.isEmpty(name)){
            Toast.makeText(SignInActivity.this,R.string.please_input_username_or_email,Toast.LENGTH_SHORT).show();
            return false;
        }
        // check password
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(SignInActivity.this,R.string.please_input_password,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void disableUi(){
        nameView.setEnabled(false);
        passView.setEnabled(false);
        signInBtn.setEnabled(false);
        signInBtn.setText(R.string.verifying);
    }

    public void enableUi(){
        nameView.setEnabled(true);
        passView.setEnabled(true);
        signInBtn.setEnabled(true);
        signInBtn.setText(R.string.sign_in_space);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}