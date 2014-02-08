package com.rise;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.base.inject.InjectView;
import com.base.inject.Injector;
import com.rise.R;
import com.rise.adapter.DragListAdapter;
import com.rise.component.BoxView;
import com.rise.component.DragListView;
import com.rise.component.DragView;

public class MainActivity extends Activity implements BoxView.BoxListener{

    @InjectView(R.id.drag_list_view)
    private DragListView dragListView;

    @InjectView(R.id.perfect_box)
    private BoxView perfectBox;
    @InjectView(R.id.uphold_box)
    private BoxView upholdBox;
    @InjectView(R.id.quick_box)
    private BoxView quickBox;
    @InjectView(R.id.bad_box)
    private BoxView badBox;


    private DragListAdapter dragListAdapter;

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

        perfectBox.setBoxListener(this);
        upholdBox.setBoxListener(this);
        quickBox.setBoxListener(this);
        badBox.setBoxListener(this);
    }

    @Override
    public void onEnter(BoxView view) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(80,0,0,0);
        view.setLayoutParams(params);
        Toast.makeText(this,"Enter",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPut(BoxView view) {
        view.setPadding(0,0,0,0);
        Toast.makeText(this,"Put",Toast.LENGTH_SHORT).show();
    }

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
}
