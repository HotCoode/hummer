package com.rise;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.base.inject.InjectView;
import com.base.inject.Injector;
import com.rise.adapter.DragListAdapter;
import com.rise.component.BoxView;
import com.rise.component.DragListView;

public class MainActivity extends Activity implements BoxView.BoxListener, View.OnClickListener {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Injector.get(this).inject();

        dragListAdapter = new DragListAdapter(this, things);
        dragListView.setAdapter(dragListAdapter);

        perfectBox.setBoxListener(this);
        upholdBox.setBoxListener(this);
        quickBox.setBoxListener(this);
        badBox.setBoxListener(this);
    }

    @Override
    public void onEnter(BoxView view) {
        hover(view, true);
//        Toast.makeText(this, "Enter", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPut(BoxView view, ClipData data) {
        hover(view, false);
        if(data != null && data.getItemCount() == 2){
            Toast.makeText(this, "Put:" + data.getItemAt(0).getText(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Put fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onExit(BoxView view) {
        hover(view, false);
    }

    /**
     * 设置hover画面响应
     *
     * @param view
     * @param isEnter
     */
    private void hover(BoxView view, boolean isEnter) {
	    View answerView = ((ViewGroup)view.getParent()).getChildAt(0);
        if (isEnter) {
	        int width = getResources().getDimensionPixelSize(R.dimen.hover_width);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) answerView.getLayoutParams();
            params.width = width;
	        answerView.setLayoutParams(params);
        } else {
	        int width = getResources().getDimensionPixelSize(R.dimen.hover_show_width);
	        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) answerView.getLayoutParams();
	        params.width = width;
	        answerView.setLayoutParams(params);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.perfect_box:
                break;
            case R.id.uphold_box:
                break;
            case R.id.quick_box:
                break;
            case R.id.bad_box:
                break;
            default:
                break;
        }
    }

    private String[] things = {"吃饭", "上班", "学习英语", "看了两小时电影", "译言网 | 怎样在一小时内学会（但不是精通）任何一种语言（加上兴趣） & 译言网 | 怎样在一小时内学会（但不精通）一门语言（附实例）", "高收益，长半衰期", "低收益，短半衰期", "高收益，短半衰期", "低收益，长半衰期", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};

}
