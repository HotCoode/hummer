package com.rise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.base.orm.QueryHelper;
import com.rise.common.AppUtils;
import com.rise.common.Const;
import com.rise.component.DoneDiscardActivity;
import com.rise.db.SQL;

/**
 * Created by kai.wang on 2/18/14.
 */
public class EditItemActivity extends DoneDiscardActivity {

    private EditText itemText;

    private String itemId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        itemId = getIntent().getStringExtra("itemId");

        String content = getIntent().getStringExtra("content");

        itemText = (EditText) findViewById(R.id.new_item_text);

        if(itemId != null && content != null){
            itemText.setText(content);
        }
    }

    @Override
    public void doneClick() {
        if (itemText.getText() != null) {
            String text = itemText.getText().toString();
            if (!"".equals(text)) {
                // edit mode
                if (itemId != null) {
                    QueryHelper.update(SQL.UPDATE_ITEM_BY_ID, new String[]{text, itemId}, null);
                    Toast.makeText(EditItemActivity.this, R.string.edit_success, Toast.LENGTH_SHORT).show();
                    sendBroadcast(new Intent(Const.ACTION_ITEM_UPDATE));
                } else {
                    // create mode
                    QueryHelper.update(SQL.ADD_ITEM, new String[]{text, System.currentTimeMillis() + ""}, null);
                    Toast.makeText(EditItemActivity.this, R.string.add_success, Toast.LENGTH_SHORT).show();
                    sendBroadcast(new Intent(Const.ACTION_ITEM_UPDATE));
                }
                AppUtils.doneActivityClose(itemText,EditItemActivity.this);
            }
        }
    }

    @Override
    public void discardClick() {
        AppUtils.discardActivityClose(itemText, this);
    }

}