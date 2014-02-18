package com.rise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.base.orm.QueryHelper;
import com.rise.common.Const;
import com.rise.component.DoneDiscardActivity;
import com.rise.db.SQL;

/**
 * Created by kai.wang on 2/18/14.
 */
public class NewItemActivity extends DoneDiscardActivity {

    private EditText itemText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item_activity);

        itemText = (EditText) findViewById(R.id.new_item_text);
    }

    @Override
    public void doneClick() {
        if (itemText.getText() != null) {
            String text = itemText.getText().toString();
            if (!"".equals(text)) {
                QueryHelper.update(SQL.ADD_ITEM, new String[]{text, System.currentTimeMillis() + ""}, null);
                Toast.makeText(NewItemActivity.this, R.string.add_success, Toast.LENGTH_SHORT).show();
                finish();
                sendBroadcast(new Intent(Const.ACTION_ITEM_UPDATE));
            }
        }
    }

    @Override
    public void discardClick() {
        finish();
    }

}