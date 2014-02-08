package com.rise;

import android.app.Activity;
import android.os.Bundle;

import com.base.inject.InjectView;
import com.base.inject.Injector;
import com.rise.R;
import com.rise.adapter.DragListAdapter;
import com.rise.component.DragListView;

public class MainActivity extends Activity {

    @InjectView(R.id.drag_list_view)
    private DragListView dragListView;

    private DragListAdapter dragListAdapter;

    private String[] things = {
            "吃饭",
            "上班",
            "学习英语",
            "看了两小时电影",
            "译言网 | 怎样在一小时内学会（但不是精通）任何一种语言（加上兴趣） & 译言网 | 怎样在一小时内学会（但不精通）一门语言（附实例）",
            "高收益，长半衰期",
            "低收益，短半衰期",
            "高收益，短半衰期",
            "低收益，长半衰期",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13"
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Injector.get(this).inject();

        dragListAdapter = new DragListAdapter(this,things);
        dragListView.setAdapter(dragListAdapter);
    }
}
