package com.rise;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import com.base.orm.QueryHelper;
import com.rise.common.AppUtils;
import com.rise.common.Const;
import com.rise.common.RiseUtil;
import com.rise.component.DoneDiscardActivity;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

/**
 * Created by kai.wang on 3/14/14.
 */
public class NewNoteActivity extends DoneDiscardActivity {

    private EditText itemText;

    private final int INSERT_NOTE_FINISH = 100;

    private String type = "";

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case INSERT_NOTE_FINISH:
                    sendBroadcast(new Intent(Const.ACTION_ITEM_UPDATE));
                    AppUtils.doneActivityClose(itemText,NewNoteActivity.this);
                    break;
            }
            return false;
        }
    });

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        type = RiseUtil.getTypeByName(getIntent().getIntExtra("id", 0));

        itemText = (EditText) findViewById(R.id.new_note_text);
    }

    @Override
    public void doneClick() {
        if (itemText.getText() != null) {
            String text = itemText.getText().toString();
            if (!"".equals(text)) {
                QueryHelper.insert(SQL.ADD_NOTE_ONLY_ITEM, new String[]{text, System.currentTimeMillis() + "", SqlConst.ITEM_STATUS_NOTE_ONLY}, new QueryHelper.InsertCallBack() {
                    @Override
                    public void onFinish(int id) {
                        if (id != 0) {
                            QueryHelper.update(SQL.PUT_NEW_NOTE, new String[]{id + "", type, System.currentTimeMillis() + ""}, null);
                            handler.sendEmptyMessage(INSERT_NOTE_FINISH);
                        }
                    }
                });

            }
        }
    }

    @Override
    public void discardClick() {
        AppUtils.discardActivityClose(itemText, this);
    }

}