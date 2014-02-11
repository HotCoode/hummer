package com.rise.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.rise.R;
import com.rise.adapter.MainListAdapter;
import com.rise.view.BoxView;
import com.rise.view.PullHeaderView;

/**
 * Created by kai.wang on 2/11/14.
 * 主页面
 */
public class MainFragment extends Fragment implements BaseFragment,BoxView.BoxListener{
    private PullHeaderView pullableListView;
    private ListView actualListView;
    private BoxView perfectBox;
    private BoxView upholdBox;
    private BoxView quickBox;
    private BoxView badBox;

    private MainListAdapter dragListAdapter;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        container = (ViewGroup) inflater.inflate(R.layout.main_fragment, null);
        injectViews(container);
        return container;
    }


    @Override
    public void injectViews(View parentView) {
        pullableListView = (PullHeaderView) parentView.findViewById(R.id.pullable_listview);
        perfectBox = (BoxView) parentView.findViewById(R.id.perfect_box);
        upholdBox = (BoxView) parentView.findViewById(R.id.uphold_box);
        quickBox = (BoxView) parentView.findViewById(R.id.quick_box);
        badBox = (BoxView) parentView.findViewById(R.id.bad_box);

        actualListView = (ListView) activity.getLayoutInflater().inflate(R.layout.main_list_view,null);
        pullableListView.createListView(actualListView);
        dragListAdapter = new MainListAdapter(activity, things);
        actualListView.setAdapter(dragListAdapter);

        perfectBox.setBoxListener(this);
        upholdBox.setBoxListener(this);
        quickBox.setBoxListener(this);
        badBox.setBoxListener(this);
    }

    @Override
    public void onEnter(BoxView view) {
        hover(view, true);
    }

    @Override
    public void onPut(BoxView view, ClipData data) {
        hover(view, false);
        if (data != null && data.getItemCount() == 2) {
            Toast.makeText(activity, "Put:" + data.getItemAt(0).getText(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Put fail", Toast.LENGTH_SHORT).show();
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
        View answerView = ((ViewGroup) view.getParent()).getChildAt(0);
        int width;
        if (isEnter) {
            width = getResources().getDimensionPixelSize(R.dimen.hover_width);
        } else {
            width = getResources().getDimensionPixelSize(R.dimen.hover_show_width);
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) answerView.getLayoutParams();
        params.width = width;
        answerView.setLayoutParams(params);
    }

    private String[] things = {"吃饭", "上班", "学习英语", "看了两小时电影", "译言网 | 怎样在一小时内学会（但不是精通）任何一种语言（加上兴趣） & 译言网 | 怎样在一小时内学会（但不精通）一门语言（附实例）", "高收益，长半衰期", "低收益，短半衰期", "高收益，短半衰期", "低收益，长半衰期", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};

}
