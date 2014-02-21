package com.rise.component;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rise.R;

/**
 * Created by kai.wang on 2/21/14.
 */
public class SimpleDialog implements View.OnClickListener{


    public interface OnClickListener{
        void onClick(View v);
    }

    private OnClickListener onClickListener;
    private AlertDialog dialog;

    public SimpleDialog(Context context,String title,String button){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setContentView(R.layout.dialog_title_button);
        TextView titleView = (TextView) dialog.findViewById(R.id.dialog_title);
        Button buttonView = (Button) dialog.findViewById(R.id.dialog_button);

        titleView.setText(title);
        buttonView.setText(button);
        buttonView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onClickListener != null) onClickListener.onClick(v);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void cancel(){
        dialog.cancel();
    }

    public void show(){
        dialog.show();
    }
}
