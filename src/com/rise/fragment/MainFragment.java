package com.rise.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.base.orm.QueryHelper;
import com.rise.R;
import com.rise.adapter.MainListAdapter;
import com.rise.bean.Item;
import com.rise.db.SQL;
import com.rise.db.SqlConst;
import com.rise.view.BoxView;
import com.rise.view.PullHeaderView;

import java.util.List;

/**
 * Created by kai.wang on 2/11/14.
 * 主页面
 */
public class MainFragment extends Fragment implements BaseFragment, BoxView.BoxListener, View.OnClickListener {
    private PullHeaderView pullableView;
    private ListView actualListView;
    private BoxView perfectBox;
    private BoxView upholdBox;
    private BoxView quickBox;
    private BoxView badBox;

    private MainListAdapter mainListAdapter;
    private Activity activity;

    private Menu menu;

    private ImageView putAnimView;
    private Animation animation;
    private Drawable circlePerfect, circleUphold, circleQuick, circleBad;

    private final int ITEM_LOAD_FINISH = 100;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == ITEM_LOAD_FINISH){
                actualListView.setAdapter(mainListAdapter);
            }
            return false;
        }
    });

    public MainFragment(Menu menu) {
        this.menu = menu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        container = (ViewGroup) inflater.inflate(R.layout.main_fragment, null);
        injectViews(container);
        return container;
    }


    @Override
    public void injectViews(View parentView) {
        pullableView = (PullHeaderView) parentView.findViewById(R.id.pullable_view);
        perfectBox = (BoxView) parentView.findViewById(R.id.perfect_box);
        upholdBox = (BoxView) parentView.findViewById(R.id.uphold_box);
        quickBox = (BoxView) parentView.findViewById(R.id.quick_box);
        badBox = (BoxView) parentView.findViewById(R.id.bad_box);
        putAnimView = (ImageView) activity.getLayoutInflater().inflate(R.layout.put_anim, null);

        actualListView = (ListView) activity.getLayoutInflater().inflate(R.layout.main_list_view, null);
        pullableView.createListView(actualListView);

        pullableView.findViewById(R.id.header_add).setOnClickListener(this);
        pullableView.findViewById(R.id.header_delete).setOnClickListener(this);
        perfectBox.setBoxListener(this);
        upholdBox.setBoxListener(this);
        quickBox.setBoxListener(this);
        badBox.setBoxListener(this);
        animation = AnimationUtils.loadAnimation(activity, R.anim.put_anim);

        menu.findItem(R.id.menu_put_anim).setActionView(putAnimView);
        circlePerfect = activity.getResources().getDrawable(R.drawable.circle_perfect);
        circleUphold = activity.getResources().getDrawable(R.drawable.circle_uphold);
        circleQuick = activity.getResources().getDrawable(R.drawable.circle_quick);
        circleBad = activity.getResources().getDrawable(R.drawable.circle_bad);

        loadData();
    }

    @Override
    public void onEnter(BoxView view) {
        hover(view, true);
    }

    @Override
    public void onPut(BoxView view, ClipData data) {
        hover(view, false);
        if (data != null && data.getItemCount() == 2) {
//            Toast.makeText(activity, "Put:" + data.getItemAt(0).getText(), Toast.LENGTH_SHORT).show();
            startPutAnim(view.getId());
        } else {
            Toast.makeText(activity, R.string.put_fail, Toast.LENGTH_SHORT).show();
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

    /**
     * 加載數據
     */
    private void loadData() {
        QueryHelper.findBeans(
                Item.class,
                SQL.FIND_ITEMS_BY_STATUS,
                new String[]{SqlConst.ITEM_STATUS_AVAILABLE},
                new QueryHelper.FindBeansCallBack<Item>() {
                    @Override
                    public void onFinish(List<Item> beans) {
                        mainListAdapter = new MainListAdapter(activity, beans);
                        handler.sendEmptyMessage(ITEM_LOAD_FINISH);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_add:
                Toast.makeText(activity, "add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.header_delete:
                Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void startPutAnim(int id) {
        switch (id) {
            case R.id.perfect_box:
                putAnimView.setImageDrawable(circlePerfect);
                break;
            case R.id.uphold_box:
                putAnimView.setImageDrawable(circleUphold);
                break;
            case R.id.quick_box:
                putAnimView.setImageDrawable(circleQuick);
                break;
            case R.id.bad_box:
                putAnimView.setImageDrawable(circleBad);
                break;
            default:
                break;
        }
        putAnimView.startAnimation(animation);
    }

}
