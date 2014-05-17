package com.rise;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.rise.component.BaseActivity;

/**
 * Created by kai.wang on 5/16/14.
 */
public class SignInActivity extends BaseActivity {
    private EditText nameView,passView;
    private Button signInBtn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        setContentView(R.layout.activity_sign_in);

        initView();

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
                    // TODO check user from server
                }else{
                    return;
                }
                break;
        }
    }

    public boolean check(){
        String name = nameView.getText().toString();
        String pass = passView.getText().toString();

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
}